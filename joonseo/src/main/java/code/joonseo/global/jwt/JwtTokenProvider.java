package code.joonseo.global.jwt;

import code.joonseo.domain.user.service.UserDetailsImpl;
import code.joonseo.domain.user.service.UserDetailsServiceImpl;
import code.joonseo.global.exception.CustomErrorCode;
import code.joonseo.global.exception.RestApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/* Component, Controller, Configuration, Service ->
    Bean으로 등록 됨 (스프링 컨테이너로 들어감??)
 */
@Component
public class JwtTokenProvider {

    private Long accessTokenExpiredTime;

    private Long refreshTokenExpiredTime;

    private final Key key;

    private final UserDetailsServiceImpl userDetailsService;

    public JwtTokenProvider(@Value("${spring.security.jwt.secret}") String secret, UserDetailsServiceImpl userDetailsService){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.userDetailsService = userDetailsService;
    }

    // Jwt 생성 (식별자 정보 --> 이메일)
    public TokenResponseDto generateToken(String email) {

        String authorities = "USER";

        Date now = new Date();

        // jwt (신분증..)
        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("auth", authorities)
                .setIssuedAt(now) // 언제 발행했느냐 (발행일)
                .setExpiration(new Date((now.getTime() + accessTokenExpiredTime))) // 만료일
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 신분증을 재발급 받기 위한 모바일 신분증...
        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date((now.getTime() + refreshTokenExpiredTime)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // http 요청으로부터 jwt 토큰 가져오는 함수
    public String getTokenFromRequest(HttpServletRequest req) {

        if (req.getHeader("Authorization") == null)
            throw new JwtException("Authorization Header is null");

        return req
                .getHeader("Authorization")
                .substring(7);
    }

    // 토큰 검증
    public boolean validateAccessToken(String accessToken) {
        if (accessToken == null)
            throw new JwtException("AccessToken is null");

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException("토큰이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            throw new JwtException("토큰의 형식이 올바르지 않습니다.");
        } catch (SignatureException | SecurityException e) {
            throw new JwtException("토큰의 서명이 올바르지 않습니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("토큰의 형식이 만료되었습니다.");
        }

    }

        public Authentication getAuthentication(String accessToken){

                Claims claims = getClaims(accessToken);

                // 권한 가져오기
                Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                try {
                    // 유저디테일스 구현 (인터페이스) , 권한 (Claims 에서 가져와서 만들수잇슴) --> 인증객체 만들어서 authentication 에 등록
                    UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(getEmailFromToken(accessToken));
                    return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
                } catch (BadCredentialsException | UsernameNotFoundException e) {
                    throw new RestApiException(CustomErrorCode.USER_NOT_FOUND);
                }
            }

            public Claims getClaims(String accessToken) {
                return Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(accessToken)
                        .getBody();
            }

            public String getEmailFromToken(String accessToken) {
                return Jwts
                        .parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJwt(accessToken)
                        .getBody()
                        .getSubject();


    }
}

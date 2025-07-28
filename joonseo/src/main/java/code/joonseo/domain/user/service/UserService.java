package code.joonseo.domain.user.service;

import code.joonseo.domain.user.dto.res.UserInfoDto;
import code.joonseo.domain.user.entity.User;
import code.joonseo.domain.user.repository.UserRepository;
import code.joonseo.global.exception.CustomErrorCode;
import code.joonseo.global.exception.RestApiException;
import code.joonseo.global.jwt.JwtTokenProvider;
import code.joonseo.global.jwt.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public User create(UserInfoDto dto) {

        // 이메일 중복 검사
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RestApiException(CustomErrorCode.DUPLICATE_EMAIL);
        }
        else {
            User user = new User(
                    null,
                    dto.getName(),
                    dto.getPart(),
                    dto.getGen(),
                    dto.getPhoneNumber(),
                    dto.getEmail(),
                    passwordEncoder.encode(dto.getPassword())
            );
            return userRepository.save(user);
        }
    }

    // 로그인
    // 로그인 성공시 클라이언트에게 JWT 토큰 반환
    public TokenResponseDto login(String email, String password) {

        // 1. 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)

                /* 사용자가 존재하지 않으면 예외 발생시킴 ->
                   Global Exception Handler 로 throw 되어 HTTP 응답으로 변환됨 */
                .orElseThrow(() -> new RestApiException(CustomErrorCode.USER_NOT_FOUND));

        // 2. 비밀번호가 일치하는지 확인
        if (passwordEncoder.matches(password, user.getPassword())) {

            // 일치하면 -> JWT 토큰 생성해 응답 DTO 에 담아 반환
            return jwtTokenProvider.generateToken(email);
        } else {

            // 일치하지 않으면 -> 예외 발생 시킴
            throw new RestApiException(CustomErrorCode.INVALID_PASSWORD);
        }


    }
}

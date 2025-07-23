package dongmin.code.dongmin.domain.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    // 보안 예외 처리
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 기본 보안 옵션
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/signup", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        // .loginPage("/login")
                        .loginProcessingUrl("/login") // 로그인 요청 URL
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/") // 로그인 성공 시 이동
                        // .failureForwardUrl("/login") // 로그인 실패 시 이동
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 요청 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 이동
                        .deleteCookies("JSESSIONID", "remember-me") // 로그아웃 후 쿠키 삭제
                        .invalidateHttpSession(true)
                )
                .httpBasic(AbstractHttpConfigurer::disable) // Http Basic Auth 기반으로 로그인 인증창이 뜸
                .csrf(AbstractHttpConfigurer::disable // 사이트 위변조 방지 설정, Post 요청 시 csrf 토큰도 보내줘야함
                );

        return http.build();
    }

    // 인증 관리자 설정
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    // 패스워드 인코더
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

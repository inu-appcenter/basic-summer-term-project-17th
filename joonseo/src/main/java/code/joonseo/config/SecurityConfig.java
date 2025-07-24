package code.joonseo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // URL 접근 권한 설정
                .authorizeHttpRequests((authorize) -> authorize
                        // 로그인, 회원가입, 메인 페이지 인증 x 접근 허용
                        .requestMatchers("/login", "signup", "/").permitAll()
                        // 그 외 모든 요청 인증 필요
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loginfrom")
                        .loginProcessingUrl("/login")
                        // 로그인 성공 시 이동할 기본 페이지
                        .defaultSuccessUrl("/")
                        // 로그인 페이지 누구나 접근 가능
                        .permitAll()
                )
                .logout((logout)->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        // 로그아웃 시 세션 무효화하여 인증 정보 삭제
                        .invalidateHttpSession(true)
                )

                // 세션 관리 설정
                .sessionManagement((sessionManagement)->sessionManagement
                        // 한 계정 당 최대 허용 세션 수 (1)
                        .maximumSessions(1)
                        // true: 중복 로그인 시 새 로그인 차단
                        // false: 새 로그인 시 기존 세션 만료 처리
                        .maxSessionsPreventsLogin(true)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

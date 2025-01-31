package dcu.fossilfuel.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.httpBasic(httpBasic -> httpBasic.disable());
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/signup", "/chatbot", "/api/**","/error").permitAll()  // 인증 필요 없음
                .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
        );
        http.formLogin(form -> form
                .loginPage("/login")  // 커스텀 로그인 페이지 경로 지정
                .defaultSuccessUrl("/")  // 로그인 성공 시 이동할 경로 지정
                .permitAll()
        );
        http.logout(logout -> logout
                .permitAll()
        );
        http.exceptionHandling(exceptions -> exceptions
                .defaultAuthenticationEntryPointFor(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**")));// CSRF 보호 비활성화
// 기본 HTTP 인증 비활성화
// 인증되지 않은 API 요청에 대해 401 응답

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

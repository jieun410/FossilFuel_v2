package dcu.fossilfuel.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler; // ✅ 핸들러 주입

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
                .requestMatchers("/", "/signup", "/chatbot", "/api/**", "/error", "/login").permitAll()
                .anyRequest().authenticated()
        );

        // ✅ form Login 사용시, 전부 스프링 시큐리티 위임 => Login controller (x)
        // ✅ 로그인 페이지 설정
        http.formLogin(login -> login
                .loginPage("/login")  // 사용자 정의 로그인 페이지
                .loginProcessingUrl("/api/auth/login") // 로그인 요청 URL (폼의 action과 일치해야 함)
                .defaultSuccessUrl("/dashboard", true) // 로그인 성공 후 리디렉션
                .failureHandler(customAuthenticationFailureHandler) // 🔥 커스텀 핸들러 등록
                .permitAll()
        );

        // ✅ 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/login?logout=true") // 로그아웃 성공 후 리디렉션
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );


        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );

        return http.build();
    }

    // ❗ AuthenticationManager 자동 주입 활용
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.jotte.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    WebSecurityConfigurerAdapter가 Deprecated 되었기 때문에
    SecurityFilterChain을 Bean으로 등록하여 사용한다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers()
                .xssProtection() // XSS 방어
                .and()
                .frameOptions().disable() // embed 모달 사용을 위해 해제
                .and()
                .csrf().disable() // CSRF 안 쓰면 해제
        ;

        http.logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    String referer = request.getHeader("Referer");
                    response.sendRedirect(referer);
                });

        return http.build();
    }
}

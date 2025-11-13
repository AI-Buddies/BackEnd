package com.example.sketchTalk.config;

import com.example.sketchTalk.security.jwt.AuthEntryPointJwt;
import com.example.sketchTalk.security.jwt.AuthTokenFilter;
import com.example.sketchTalk.security.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthTokenFilter authTokenFilter(JwtUtils jwtUtils) {
        return new AuthTokenFilter(jwtUtils);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthEntryPointJwt authEntryPointJwt, JwtUtils jwtUtils) throws Exception {

        // CSRF 비활성화, CORS 활성화
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults());

        // 세션을 생성하지 않음
        http
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .exceptionHandling(e -> e.authenticationEntryPoint(authEntryPointJwt));

        // URL 접근
        http
                .authorizeHttpRequests((auth -> auth
                        // 다른 도메인도 허용
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 인증 필요 없는 엔드포인트
                        .requestMatchers(
                                "/error",
                                "/health-check/**",
                                "/user/register",
                                "/user/login",
                                "/user/id/availability",
                                "/refresh"
                        ).permitAll()

                        // 그 외 인증 필요
                        .anyRequest().authenticated()
                ));

        http
                .addFilterAfter(authTokenFilter(jwtUtils), ExceptionTranslationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
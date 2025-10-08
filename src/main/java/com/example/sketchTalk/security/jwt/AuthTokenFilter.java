package com.example.sketchTalk.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    public AuthTokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            if (jwt != null) {
                // 1. 토큰 검증
                jwtUtils.validateJwtToken(jwt);

                // 2. userId 추출
                Long userId = jwtUtils.getUserIdFromJwtToken(jwt);

                // 3. Authentication 생성
                var authentication =  new UsernamePasswordAuthenticationToken(userId, null, List.of());

                // 4. 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (InvalidJwtAuthenticationException e) {
            request.setAttribute("authErrorMessage", e.getMessage());
            throw new BadCredentialsException(e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}

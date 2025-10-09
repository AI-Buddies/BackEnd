package com.example.sketchTalk.security.jwt;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk._core.error.BaseErrorCode;
import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.exception.token.JwtExceptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public AuthEntryPointJwt(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e
    ) throws IOException {

        // 기본은 INVALID_TOKEN
        BaseErrorCode code = JwtExceptions.INVALID_TOKEN;

        Throwable cause = e.getCause();

        // 실제 비교
        if (cause instanceof CustomException ce) {
            code = ce.getErrorCode();
        }

        // 헤더 설정
        // 응답 바디 JSON임을 명시
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8");
        // HTTP 401 상태 코드 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 한글 깨짐 방지
        response.setCharacterEncoding("UTF-8");

        // 바디 설정
        ApiResponse<?> body = ApiResponse.onFailure(code);

        // JSON 직렬화
        objectMapper.writeValue(response.getWriter(), body);

        // 순서를 안바꾸고 바로 exception 던지기
        return;
    }
}
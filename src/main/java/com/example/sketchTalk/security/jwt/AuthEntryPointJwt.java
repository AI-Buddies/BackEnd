package com.example.sketchTalk.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    ) throws ServletException, IOException {

        // 응답 바디 JSON임을 명시
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8");
        // HTTP 401 상태 코드 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 한글 깨짐 방지
        response.setCharacterEncoding("UTF-8");

        final Map<String, Object> body = new HashMap<>();

        // TODO: body 양식 맞추기
        body.put("statusCode", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("message", e.getMessage());
        body.put("data", request.getServletPath());
        body.put("isSuccess", false);

        // JSON 직렬화
        objectMapper.writeValue(response.getWriter(), body);
    }
}

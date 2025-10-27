package com.example.sketchTalk.exception.token;

import com.example.sketchTalk._core.error.BaseErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtExceptions implements BaseErrorCode {
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT1", "잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT2", "JWT 토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT3", "지원하지 않는 JWT 형식입니다."),
    EMPTY_CLAIMS(HttpStatus.BAD_REQUEST, "JWT4", "JWT 클레임이 비어 있습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT5", "JWT 검증에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    public static JwtExceptions from(Throwable t) {
        if (t instanceof ExpiredJwtException) return EXPIRED_TOKEN;
        if (t instanceof MalformedJwtException) return MALFORMED_TOKEN;
        if (t instanceof UnsupportedJwtException) return UNSUPPORTED_TOKEN;
        if (t instanceof IllegalArgumentException) return EMPTY_CLAIMS;
        if (t instanceof JwtException) return INVALID_TOKEN;
        return INVALID_TOKEN;
    }
}

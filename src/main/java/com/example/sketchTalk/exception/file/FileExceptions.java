package com.example.sketchTalk.exception.file;

import com.example.sketchTalk._core.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileExceptions implements BaseErrorCode {
    FAIL_TO_UPLOAD(HttpStatus.BAD_REQUEST, "F001", "S3 버킷 업로드 과정에서 문제 발생"),
    FAIL_TO_STREAM(HttpStatus.BAD_REQUEST, "F002", "파일 스트림 처리 중 오류 발생"),
    CLIENT_ERROR(HttpStatus.BAD_REQUEST, "F003", "AWS Client 오류 발생");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

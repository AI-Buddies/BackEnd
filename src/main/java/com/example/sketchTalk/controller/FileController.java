package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.test.S3Res;
import com.example.sketchTalk.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final S3Service s3Service;

    @PostMapping("/test/s3")
    public ApiResponse<S3Res> testS3(@RequestPart("img") MultipartFile img) throws IOException {
        S3Res res = s3Service.uploadImage("test/", img);
        return ApiResponse.onSuccess(HttpStatus.OK, res);
    }
}

package com.example.sketchTalk.service;

import com.example.sketchTalk._core.util.S3Utils;
import com.example.sketchTalk.dto.test.S3Res;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Utils s3Utils;

    public S3Res uploadImage(String dir, MultipartFile img) {
        return s3Utils.uploadFileToS3(dir, img);
    }
}

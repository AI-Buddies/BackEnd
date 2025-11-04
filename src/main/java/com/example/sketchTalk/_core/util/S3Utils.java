package com.example.sketchTalk._core.util;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.test.S3Res;
import com.example.sketchTalk.exception.file.FileExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Utils {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.url}")
    private String s3URL;

    public S3Res uploadFileToS3(String dir, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension =originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = dir + UUID.randomUUID().toString() + extension;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(bucket, fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            throw new CustomException(FileExceptions.FAIL_TO_STREAM);
        } catch (SdkClientException e) {
            throw new CustomException(FileExceptions.CLIENT_ERROR);
        } catch (Exception e) {
            throw new RuntimeException("S3 버킷 업로드 과정에서 문제 발생");
        }
        String fileURL = s3URL + fileName;
        return new S3Res(fileURL);
    }
}

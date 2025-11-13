package com.example.sketchTalk.config.firebase;

import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FirebaseProperties {
    private Resource serviceAccount;
    private String projectId;
}
package com.example.sketchTalk.config.firebase;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {
    private Resource serviceAccount;
    private String projectId;
}
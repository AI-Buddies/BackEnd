package com.example.sketchTalk.exception.setting;

import org.springframework.http.HttpStatus;

public class SettingException extends RuntimeException {
    private final SettingExceptions settingExceptions;

    public SettingException(SettingExceptions settingExceptions) {
        this.settingExceptions = settingExceptions;
    }

    public HttpStatus getHttpStatus() {
        return settingExceptions.getStatus();
    }

    public String getMessage() {
        return settingExceptions.getMessage();
    }
}

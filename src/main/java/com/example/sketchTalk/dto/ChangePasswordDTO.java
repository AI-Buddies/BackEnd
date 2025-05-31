package com.example.sketchTalk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
    private String loginId;
    private String oldPassword;
    private String newPassword;
}

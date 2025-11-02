package com.example.sketchTalk.dto.user.in;

public record ChangePasswordReq(
        String loginId,
        String oldPassword,
        String newPassword
){
}
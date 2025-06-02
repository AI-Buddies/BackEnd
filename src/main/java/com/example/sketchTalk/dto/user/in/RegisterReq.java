package com.example.sketchTalk.dto.user.in;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegisterReq {
    public String loginId;
    public String password;
    public String nickname;
    public LocalDate birthdate;
}

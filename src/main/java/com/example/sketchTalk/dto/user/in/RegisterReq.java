package com.example.sketchTalk.dto.user.in;

import java.time.LocalDate;

public record RegisterReq(
        String loginId,
        String password,
        String nickname,
        LocalDate birthdate
){
}
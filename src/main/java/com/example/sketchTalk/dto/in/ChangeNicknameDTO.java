package com.example.sketchTalk.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNicknameDTO {
    public String loginId;
    public String password;
    public String newNickname;
}

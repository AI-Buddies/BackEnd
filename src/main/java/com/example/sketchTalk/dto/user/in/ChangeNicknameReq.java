package com.example.sketchTalk.dto.user.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNicknameReq {
    public String loginId;
    public String password;
    public String newNickname;
}

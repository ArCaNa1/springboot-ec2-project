package com.example.myapp.Membership.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String userId;
    private String email;
    private String password;  // 실제 비밀번호 (회원가입 시 사용, 저장 시 해싱)
    private String nickname;
}


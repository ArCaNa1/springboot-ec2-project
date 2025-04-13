package com.example.myapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {
    private Long user_id;
    private String email;
    private String nickname;
    private String message;
}

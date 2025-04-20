package com.example.myapp.Membership.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckUserIdResponse {
    private boolean is_duplicate;

}

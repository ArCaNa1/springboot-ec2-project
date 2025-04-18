package com.example.myapp.Membership.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamJoinRequest {
    private String userId;
    private Integer teamId;
}

package com.example.myapp.Membership.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamCreateRequest {
    private String teamName;
    private String teamDescription;
    private int maxMember;
    private String teamTier;
}

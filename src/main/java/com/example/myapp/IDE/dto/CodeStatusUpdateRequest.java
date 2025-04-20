package com.example.myapp.IDE.dto;

import lombok.Data;


@Data
public class CodeStatusUpdateRequest {
    private Integer team_id;
    private Long quest_id;
    private String user_id;
    private String quest_status;
}

package com.example.myapp.IDE.dto;

import lombok.Data;

@Data
public class CodeRunRequest {
    private String code_context;
    private String language;
}
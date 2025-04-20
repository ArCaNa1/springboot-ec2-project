package com.example.myapp.IDE.dto;

import lombok.Data;

@Data
public class JdoodleResponse {
    private String output;
    private String stderr;
    private Integer statusCode;
    private String memory;
    private String cpuTime;
}
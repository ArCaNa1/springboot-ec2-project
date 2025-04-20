package com.example.myapp.IDE.dto;

import lombok.Data;

@Data
public class JdoodleRequest {
    private String script;
    private String language;
    private String clientId;
    private String clientSecret;

}

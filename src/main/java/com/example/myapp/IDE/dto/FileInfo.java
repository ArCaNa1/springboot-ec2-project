package com.example.myapp.IDE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    private Long file_id;
    private String file_name;
    private String code_content;
    private String language;
}
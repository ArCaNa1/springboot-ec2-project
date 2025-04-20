package com.example.myapp.IDE.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeIndexRequest {
    private Long folder_id;
    private Long parent_id;
}
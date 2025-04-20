package com.example.myapp.IDE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderInfo {
    private Long folder_id;
    private String folder_name;
    private Long parent_id;
    private List<FileInfo> files;
}
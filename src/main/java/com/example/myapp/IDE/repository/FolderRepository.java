package com.example.myapp.IDE.repository;

import com.example.myapp.IDE.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByParentId_FolderId(Long parentId);
    List<Folder> findByFiles_Submission_SubmitId(Integer submitId);
}
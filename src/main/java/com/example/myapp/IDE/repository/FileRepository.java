package com.example.myapp.IDE.repository;

import com.example.myapp.IDE.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFolder_FolderIdAndSubmission_UserId_UserIdAndSubmission_QuestId_QuestId(
            Long folder_FolderId, String submission_UserId_UserId, Integer submission_QuestId_QuestId);

    List<File> findBySubmission_UserId_UserIdAndSubmission_QuestId_QuestId(String userId, Integer questId);

    List<File> findByFolder_FolderId(Long folderId);

    List<File> findBySubmission_SubmitId(Integer submitId);
}
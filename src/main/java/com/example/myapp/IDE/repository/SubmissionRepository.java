package com.example.myapp.IDE.repository;

import com.example.myapp.IDE.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    Submission findByQuestId_QuestIdAndUserId_UserId(Integer questId, String userId);
    List<Submission> findByUserId_UserIdAndQuestId_TeamId_TeamId(String userId, Integer teamId);
    List<Submission> findByQuestId_QuestId(Integer questId);
    List<Submission> findByUserId_UserId(String userId);
}
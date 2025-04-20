package com.example.myapp.IDE.repository;

import com.example.myapp.IDE.entity.Quest;
import com.example.myapp.Membership.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestRepository extends JpaRepository<Quest, Integer> {
    Optional<Quest> findByQuestIdAndCreatorId(Integer questId, User userId);
}
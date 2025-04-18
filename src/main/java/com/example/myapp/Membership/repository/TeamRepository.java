package com.example.myapp.Membership.repository;

import com.example.myapp.Membership.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}

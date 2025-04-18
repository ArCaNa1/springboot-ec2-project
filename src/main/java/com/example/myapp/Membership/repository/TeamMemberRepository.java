package com.example.myapp.Membership.repository;

import com.example.myapp.Membership.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    // 특정 유저가 속한 팀 멤버 정보 조회
    Optional<TeamMember> findByUser_UserId(String userId);

    // 유저가 특정 팀에 이미 가입했는지 확인
    boolean existsByUser_UserIdAndTeam_TeamId(String userId, Integer teamId);
}

package com.example.myapp.Membership.repository;


import com.example.myapp.Membership.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, String> {
    // 특정 유저 ID로 팀 멤버 정보 조회
    Optional<TeamMember> findByUser_UserId(String userId);
}

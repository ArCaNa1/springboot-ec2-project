package com.example.myapp.Membership.service;

import com.example.myapp.Membership.dto.TeamCreateRequest;
import com.example.myapp.Membership.entity.Team;
import com.example.myapp.Membership.entity.TeamMember;
import com.example.myapp.Membership.entity.User;
import com.example.myapp.Membership.repository.TeamMemberRepository;
import com.example.myapp.Membership.repository.TeamRepository;
import com.example.myapp.Membership.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;  // 추가

    public TeamService(TeamRepository teamRepository, UserRepository userRepository, TeamMemberRepository teamMemberRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    // 팀 생성
    public Team createTeam(TeamCreateRequest request, String userId) {
        User leader = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("리더 유저를 찾을 수 없습니다."));

        Team team = new Team(
                request.getTeamName(),
                request.getTeamDescription(),
                request.getMaxMember(),
                request.getTeamTier(),
                leader
        );

        return teamRepository.save(team);
    }

    // 팀 상세 조회
    public Team getTeamById(Integer teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("해당 팀이 존재하지 않습니다."));
    }

    // 팀원 목록 조회
    public List<User> getTeamMembers(Integer teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("해당 팀이 존재하지 않습니다."));

        return team.getMembers().stream()
                .map(TeamMember::getUser)
                .collect(Collectors.toList());
    }

    // 팀 참가
    public String joinTeam(String userId, Integer teamId) {
        if (teamMemberRepository.existsByUser_UserIdAndTeam_TeamId(userId, teamId)) {
            throw new IllegalStateException("이미 참가한 팀입니다.");
        }

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

        // 현재 인원 증가
        team.setCurrentMemberCount(team.getCurrentMemberCount() + 1);
        teamRepository.save(team);

        // 팀멤버 등록
        TeamMember teamMember = new TeamMember(null, user, team);
        teamMemberRepository.save(teamMember);

        return "팀 참가 완료";
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}

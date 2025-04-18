package com.example.myapp.Membership.controller;

import com.example.myapp.Membership.dto.TeamCreateRequest;
import com.example.myapp.Membership.dto.TeamJoinRequest;
import com.example.myapp.Membership.entity.Team;
import com.example.myapp.Membership.entity.User;
import com.example.myapp.Membership.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamCreateRequest request) {
        // 테스트용: 고정 userId 사용 (JWT 없이)
        String userId = "testuser";

        try {
            Team team = teamService.createTeam(request, userId);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("팀 생성 실패: " + e.getMessage());
        }
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<?> getTeamInfo(@PathVariable Integer teamId) {
        try {
            Team team = teamService.getTeamById(teamId);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("팀 조회 실패: " + e.getMessage());
        }
    }

    @GetMapping("/{teamId}/member")
    public ResponseEntity<?> getTeamMembers(@PathVariable Integer teamId) {
        try {
            List<User> members = teamService.getTeamMembers(teamId);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("팀원 조회 실패: " + e.getMessage());
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinTeam(@RequestBody TeamJoinRequest request) {
        try {
            String result = teamService.joinTeam(request.getUserId(), request.getTeamId());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("팀 참가 실패: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getTeamList() {
        try {
            List<Team> teams = teamService.getAllTeams();
            return ResponseEntity.ok(teams);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("팀 리스트 조회 실패: " + e.getMessage());
        }
    }
}

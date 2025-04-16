package com.example.myapp.Membership.service;

import com.example.myapp.Membership.dto.*;
import com.example.myapp.Membership.entity.TeamMember;
import com.example.myapp.Membership.repository.TeamMemberRepository;
import com.example.myapp.Membership.util.JwtTokenProvider;
import com.example.myapp.Membership.entity.User;
import com.example.myapp.Membership.repository.UserRepository;
import com.example.myapp.Membership.util.PasswordUtil;
import com.example.myapp.Membership.util.TierUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.Random;
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TeamMemberRepository teamMemberRepository;
    private final SolvedAcService solvedAcService;



    public UserService(UserRepository userRepository, EmailService emailService, TeamMemberRepository teamMemberRepository,SolvedAcService solvedAcService ) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.teamMemberRepository = teamMemberRepository;
        this.solvedAcService = solvedAcService;


    }


    //회원가입 로직
    public User register(RegisterRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        // SHA256 해싱 적용
        String hashedPassword = PasswordUtil.hashPassword(request.getPassword());


        String tier = "Bronze"; // 테스트니까 하드코딩 가능

        User user = new User(
                request.getUserId(),
                request.getNickname(),
                request.getEmail(),
                hashedPassword,
                tier
        );

        return userRepository.save(user);
    }

    // 아이디 중복 로직
    public boolean isUserIdDuplicate(String userId) {
        try {
            return userRepository.existsByUserId(userId);  // 예시: 유저 존재 여부 체크
        } catch (Exception e) {
            // 로그로 예외를 찍어서 문제 추적
            log.error("Error checking duplicate user ID: ", e);
            throw new RuntimeException("Database error occurred");
        }
    }


    //로그인 로직
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 잘못되었습니다."));

        String hashedPassword = PasswordUtil.hashPassword(request.getPassword());

        if (!user.getHashedPassword().equals(hashedPassword)) {
            throw new IllegalArgumentException("비밀번호가 잘못되었습니다.");
        }

        // JWT 생성(아이디와 티어정보를 같이 가지고 있음)
        String token = JwtTokenProvider.createToken(user.getUserId(), user.getTier());

        return new LoginResponse(token, "로그인 성공");
    }

    //아이디 찾기 로직
    public FindIdResponse findUserIdByEmail(FindIdRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일에 대한 아이디가 없습니다."));
        return new FindIdResponse(user.getUserId());
    }


    //비밀번호 재설정하여 이메일로 보내는 로직
    public void resetPasswordAndSendEmail(FindPasswordRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if(!user.getEmail().equals(request.getEmail())){
            throw new IllegalArgumentException("이메일이 일치하지 않습니다.");
        }

        //임시 비밀번호 생성하는 로직
        String tempPassword = generateRandomPassword();
        String hashedPassword = PasswordUtil.hashPassword(tempPassword);

        //비밀번호 갱신하는 로직
        user.setHashedPassword(hashedPassword);
        userRepository.save(user);

        //이메일 전송(임시 비밀번호도 같이)
        emailService.sendTempPasswordEmail(request.getEmail(), tempPassword);

    }

    //임시 비밀번호 생성 로직
    private String generateRandomPassword() {
        int length = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }


    //유저정보조회 로직
    public UserInfoResponse getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 입니다."));

        Optional<TeamMember> teamMember = teamMemberRepository.findByUser_UserId(userId);
        Integer teamId = teamMember.map(tm -> tm.getTeam().getTeamId()).orElse(null);

        // UserInfoResponse 객체 생성
        return new UserInfoResponse(
                user.getNickname(),
                user.getEmail(),
                user.getTier(),
                teamId
        );
    }


}

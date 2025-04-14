package com.example.myapp.Membership.service;

import com.example.myapp.Membership.util.JwtTokenProvider;
import com.example.myapp.Membership.dto.LoginRequest;
import com.example.myapp.Membership.dto.LoginResponse;
import com.example.myapp.Membership.dto.RegisterRequest;
import com.example.myapp.Membership.entity.User;
import com.example.myapp.Membership.repository.UserRepository;
import com.example.myapp.Membership.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //회원가입 로직
    public User register(RegisterRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        // SHA-256 해싱 적용
        String hashedPassword = PasswordUtil.hashPassword(request.getPassword());

        User user = new User(
                request.getUserId(),
                hashedPassword,
                request.getNickname(),
                request.getEmail(),
                "UNRANKED"  // 기본 티어 설정
        );

        return userRepository.save(user);
    }

    //아이디 중복 로직
    public boolean isUserIdDuplicate(String userId) {
        return userRepository.existsByUserId(userId);
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
    public User findUserIdByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일에 대한 유저가 없습니다."));
    }


    //비밀번호 찾기 로직
    public User resetPasswordAndSendEmail(String userId, String email) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if(!user.getEmail().equals(email)){
            throw new IllegalArgumentException("이메일이 일치하지 않습니다.");
        }

        //임시 비밀번호 생성하는 로직
        String tempPassword = generateRandomPassword();
        String hashedPassword = PasswordUtil.hashPassword(tempPassword);

        //비밀번호 갱신하는 로직
        user.setHashedPassword(hashedPassword);
        userRepository.save(user);

        //이메일 전송(임시 비밀번호도 같이)
        EmailService.sendTempPasswordEmail(email, tempPassword);

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


}

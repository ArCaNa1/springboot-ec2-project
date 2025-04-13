package com.example.myapp.service;

import com.example.myapp.dto.LoginRequest;
import com.example.myapp.dto.LoginResponse;
import com.example.myapp.entity.User;
import com.example.myapp.dto.RegisterRequest;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository= userRepository;
    }

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setHashedPassword(hashedPassword);
        user.setNickname(request.getNickname());
        user.setTier("Silver");

        userRepository.save(user);
    }


    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getHashedPassword())) {
            throw new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다.");
        }

        String token = jwtTokenProvider.createToken(user.getEmail());
        return new LoginResponse(token);  // LoginResponse 객체 반환
    }


}

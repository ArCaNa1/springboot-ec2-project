package com.example.myapp.controller;

import com.example.myapp.dto.LoginRequest;
import com.example.myapp.dto.LoginResponse;
import com.example.myapp.dto.RegisterRequest;
import com.example.myapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("회원가입 성공"));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 로그인 시 JWT 토큰 반환
            String token = String.valueOf(userService.login(loginRequest));
            return ResponseEntity.ok(new LoginResponse(token));  // 성공적으로 로그인 시 토큰 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));  // 로그인 실패 시 에러 메시지
        }
    }


    // 응답 메시지를 표현하는 클래스
    public static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}


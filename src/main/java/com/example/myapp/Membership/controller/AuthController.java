package com.example.myapp.Membership.controller;

import com.example.myapp.Membership.dto.LoginRequest;
import com.example.myapp.Membership.dto.LoginResponse;
import com.example.myapp.Membership.dto.RegisterRequest;
import com.example.myapp.Membership.dto.RegisterResponse;
import com.example.myapp.Membership.entity.User;
import com.example.myapp.Membership.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    //회원가입 엔드포인트
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            //신규
            RegisterResponse response = new RegisterResponse("회원가입 성공");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalStateException e) {
            // 이미 존재하는 경우
            RegisterResponse errorResponse = new RegisterResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            // 예상치 못한 에러 처리
            RegisterResponse errorResponse = new RegisterResponse("서버 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    //아이디 중복 엔드포인트
    @GetMapping("/check-id/{userId}")
    public ResponseEntity<?> checkUserIdDuplicate(@PathVariable String userId) {
        boolean isDuplicate = userService.isUserIdDuplicate(userId);
        return ResponseEntity.ok(Map.of("isDuplicate", isDuplicate));
    }

    //로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            LoginResponse response = userService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message","서버오류: " + e.getMessage()));
        }

    }

    //아이디 찾기 엔드포인트
    @PostMapping("/find-id")
    public ResponseEntity<?> findUserId(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "이메일을 입력해주세요."));
        }

        User user = userService.findUserIdByEmail(email);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "해당 이메일에 해당하는 아이디가 없습니다."));
        }
        return ResponseEntity.ok(Map.of("userId", user.getUserId()));
    }

    //비밀번호 찾기 엔드포인트
    @PostMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody Map<String, String> request){
        String userId = request.get("userId");
        String email = request.get("email");
        
        try{
            userService.resetPasswordAndSendEmail(userId, email);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
        
    }


}

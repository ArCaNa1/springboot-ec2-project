package com.example.myapp.Membership.controller;

import com.example.myapp.Membership.dto.*;
import com.example.myapp.Membership.entity.User;
import com.example.myapp.Membership.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    //회원가입 엔드포인트
    @PostMapping("/signup")
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


    // 아이디 중복 엔드포인트
    @GetMapping("/check-id/{userId}")
    public ResponseEntity<?> checkUserIdDuplicate(@PathVariable("userId") String userId) {
        try {
            boolean isDuplicate = userService.isUserIdDuplicate(userId);
            CheckUserIdResponse response = new CheckUserIdResponse(isDuplicate);
            return ResponseEntity.ok(response);  // CheckUserIdResponse 객체를 반환
        } catch (Exception e) {
            log.error("Error checking user ID: ", e);
            // 예외 발생 시에도 CheckUserIdResponse 형태로 반환
            CheckUserIdResponse response = new CheckUserIdResponse(false); // 기본값 false로 설정
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // 오류 시에도 CheckUserIdResponse 반환
        }
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
    public ResponseEntity<FindIdResponse> findUserId(@RequestBody FindIdRequest request) {
        FindIdResponse response = userService.findUserIdByEmail(request);
        return ResponseEntity.ok(response);
    }

    //비밀번호 찾기 엔드포인트
    @PostMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequest request) {
        userService.resetPasswordAndSendEmail(request);
        return ResponseEntity.ok(Map.of("success", true));
    }

    //로그아웃 엔드포인트
    @PostMapping("/logout")
    public ResponseEntity<?> logout()
    {
        return ResponseEntity.ok(Map.of("message", "로그아웃됐습니다."));

    }


}


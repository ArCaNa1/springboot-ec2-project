package com.example.myapp.Membership.controller;

import com.example.myapp.Membership.dto.UserInfoResponse;
import com.example.myapp.Membership.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //유저정보조회 엔드포인트
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable("userId") String userId) {
        UserInfoResponse response = userService.getUserInfo(userId);
        return ResponseEntity.ok(response);
    }
}

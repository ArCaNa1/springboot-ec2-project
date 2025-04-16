package com.example.myapp.Membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    private final String REGISTER_URL = "http://3.104.230.163:8080/auth/register";

    public String registerUser(String userId, String password, String nickname, String email, String jwtToken) {
        // 요청 본문을 담을 객체 (RegisterRequest)
        String requestBody = String.format("{\"userId\": \"%s\", \"password\": \"%s\", \"nickname\": \"%s\", \"email\": \"%s\"}",
                userId, password, nickname, email);

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // JWT 토큰을 Authorization 헤더에 추가
        headers.set("Authorization", "Bearer " + jwtToken);  // JWT 토큰을 Authorization 헤더에 추가

        // HttpEntity 객체 생성 (헤더와 본문 포함)
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // POST 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(REGISTER_URL, HttpMethod.POST, requestEntity, String.class);

            // 응답 처리
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // 오류 처리
            return "Error: " + e.getResponseBodyAsString();
        }
    }
}

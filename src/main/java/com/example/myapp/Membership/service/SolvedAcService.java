package com.example.myapp.Membership.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SolvedAcService {

    private final RestTemplate restTemplate;

    public SolvedAcService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int fetchTier(String userId) {
        String url = "https://solved.ac/api/v3/user/show?handle=" + userId;

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Map body = response.getBody();
                return (int) body.get("tier");
            } else {
                throw new RuntimeException("Solved.ac API 호출 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Solved.ac 호출 중 오류 발생", e);
        }
    }
}
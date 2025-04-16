package com.example.myapp;

import com.example.myapp.Membership.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MyappApplication {

	@Autowired
	private AuthService authService;

	public static void main(String[] args) {
		SpringApplication.run(MyappApplication.class, args);
	}

/*	@Override
	public void run(String... args) throws Exception {
		// 로그인 후 받은 JWT 토큰을 예시로 설정 (여기서는 실제 JWT 토큰을 사용해야 함)
		String jwtToken = "your_jwt_token_here";  // 예시로 실제 JWT 토큰을 사용해야 합니다.

		// 회원가입 요청 보내기
		String result = authService.registerUser("newUser123", "password123", "newNickname", "newUser@example.com", jwtToken);
		System.out.println(result);  // 결과 출력
	}*/
}

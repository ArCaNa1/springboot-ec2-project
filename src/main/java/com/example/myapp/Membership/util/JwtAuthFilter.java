package com.example.myapp.Membership.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;

@Component
public class JwtAuthFilter implements HandlerInterceptor {

    public class SecretKey {
        public static String JWT_SECRT_KEY = "goormwebidprojectfirst"; //임의로 짠 키
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        // 로그 추가: Authorization 헤더가 있는지 확인
        System.out.println("Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 로그 추가: Authorization 헤더가 없거나 "Bearer "로 시작하지 않으면 401 반환
            System.out.println("No Authorization header or invalid format.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("인증 토큰이 필요합니다.");
            return false;
        }

        String token = authHeader.substring(7);

        try {
            // 로그 추가: 토큰을 파싱하여 Claims 추출
            System.out.println("Parsing token: " + token);
            Claims claims = Jwts.parser()
                    .setSigningKey(SecretKey.JWT_SECRT_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 로그 추가: 인증이 성공하면 claims 값 출력
            System.out.println("Token parsed successfully. Claims: " + claims);

            // 인증성공
            request.setAttribute("userId", claims.get("id"));
            request.setAttribute("tier", claims.get("tier"));
            return true;
        } catch (Exception e) {
            // 로그 추가: 예외 발생 시 401 반환
            System.out.println("Error parsing token: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("유효하지 않은 토큰입니다.");
            return false;
        }
    }
}

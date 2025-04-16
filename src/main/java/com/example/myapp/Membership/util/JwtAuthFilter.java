package com.example.myapp.Membership.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter implements HandlerInterceptor {

    // 비밀키를 명시적으로 코드 내에 하드코딩
    private static final String SECRET_KEY = "mysupersecureandlongenoughsecretkeyandwith123456"; // 여기에 비밀키 명시

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 인증이 필요 없는 경로는 예외 처리
        String path = request.getRequestURI();
        if (path.startsWith("/auth/")) {
            return true;  // 인증 없이 요청을 계속 처리
        }

        // Authorization 헤더에서 토큰 추출
        String authHeader = request.getHeader("Authorization");

        // 로그 추가: Authorization 헤더가 있는지 확인
        System.out.println("Authorization header: " + authHeader);

        // Authorization 헤더가 없거나 잘못된 형식일 경우
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No Authorization header or invalid format.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("인증 토큰이 필요합니다.");
            return false;
        }

        // Bearer 앞부분 제거하고 토큰만 추출
        String token = authHeader.substring(7);

        try {
            // 토큰 파싱하여 Claims 추출
            System.out.println("Parsing token: " + token);
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 토큰 파싱 성공
            System.out.println("Token parsed successfully. Claims: " + claims);

            // 인증 정보 사용자 ID와 티어 저장
            request.setAttribute("userId", claims.get("id"));
            request.setAttribute("tier", claims.get("tier"));
            return true;
        } catch (Exception e) {
            // 예외 발생 시 처리
            System.out.println("Error parsing token: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("유효하지 않은 토큰입니다.");
            return false;
        }
    }
}

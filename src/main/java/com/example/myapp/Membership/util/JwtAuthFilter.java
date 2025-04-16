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

    public class SecretKey{
        public static String JWT_SECRT_KEY = "goormwebidprojectfirst"; //임의로 짠 키
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception{
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("인증 토큰이 필요합니다.");
            return false;
        }

        String token = authHeader.substring(7);

        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(SecretKey.JWT_SECRT_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            //인증성공
            request.setAttribute("userId", claims.get("id"));
            request.setAttribute("tier", claims.get("tier"));
            return true;
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("유효하지 않은 토큰입니다.");
            return false;
        }

    }

}

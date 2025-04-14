package com.example.myapp.Membership.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class extractInfoFromToken {

    public static String extractUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JwtTokenProvider.SecretKey.JWT_SECRT_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public static String extractTierFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JwtTokenProvider.SecretKey.JWT_SECRT_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("tier", String.class);
    }
}

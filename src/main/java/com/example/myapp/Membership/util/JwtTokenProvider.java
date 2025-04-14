package com.example.myapp.Membership.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenProvider {
    public class SecretKey{
        public static String JWT_SECRT_KEY = "helloworld"; //임의로 짠 키
    }


    public static String createToken(String userId, String tier){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt") //Header값 ("alg", "typ")을 설정
                .claim("tier", tier) //쿠키에 저장되는 유저 데이터
                .claim("id", userId) //쿠키에 저장되는 유저 데이터
                .setSubject(userId) //tier 를 가진 유저의 인증 정보를 담고 있다는 토큰이라는 말
                .setIssuedAt(now) //토큰 생성 시간
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*356))) //토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, SecretKey.JWT_SECRT_KEY)
                .compact(); //토큰 생성

    }
}

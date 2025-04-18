//package com.example.myapp.Membership.util;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//public class PasswordUtil {
//
//    public static String hashPassword(String password) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hashBytes = md.digest(password.getBytes());
//
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashBytes) {
//                hexString.append(String.format("%02x", b));  // 16진수로 변환
//            }
//
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("SHA-256 해시 알고리즘을 찾을 수 없습니다.");
//        }
//    }
//}


package com.example.myapp.Membership.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    // 비밀번호를 해시(bcrypt 방식)로 변환
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    // 비밀번호 비교: 입력값 vs 해시값
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }
}
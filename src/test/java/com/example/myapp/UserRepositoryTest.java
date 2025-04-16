package com.example.myapp;

import com.example.myapp.Membership.repository.UserRepository;
import com.example.myapp.Membership.entity.User;
import com.example.myapp.Membership.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserSavedToDatabase() {
        // Given: 저장할 사용자 정보
        String userId = "newUser123";
        String password = "password123";
        String nickname = "shortNickname";
        String email = "newUser@example.com";
        String tier = "Gold";

        // When: 비밀번호 해싱 후 회원가입 메소드 호출
        String hashedPassword = PasswordUtil.hashPassword(password); // 비밀번호 해싱
        User user = new User(userId, hashedPassword, nickname, email, tier);
        userRepository.save(user);

        // Then: DB에 저장된 사용자 정보 확인
        User savedUser = userRepository.findByUserId(userId).orElse(null);
        assertNotNull(savedUser);
        assertEquals(userId, savedUser.getUserId());
        assertEquals(nickname, savedUser.getNickname());
        assertEquals(email, savedUser.getEmail());
        assertEquals(tier, savedUser.getTier());
    }
}
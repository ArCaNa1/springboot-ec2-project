package com.example.myapp.Membership.service;

import com.example.myapp.Membership.dto.RegisterRequest;
import com.example.myapp.Membership.entity.User;
import com.example.myapp.Membership.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SolvedAcService solvedAcService;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_ShouldSucceed_WhenValidRequest() {
        RegisterRequest request = new RegisterRequest("newUser", "email@test.com", "password", "닉네임");
        User user = new User("newUser", "닉네임", "email@test.com", "password", "BRONZE");

        when(solvedAcService.fetchTier("newUser")).thenReturn(5);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.register(request);

        assertNotNull(savedUser);
        assertEquals("newUser", savedUser.getUserId());
    }
}

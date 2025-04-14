package com.example.myapp.Membership.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.myapp.Membership.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    boolean existsByUserId(String userId);
    Optional<User> findByUserId(String userId);

}


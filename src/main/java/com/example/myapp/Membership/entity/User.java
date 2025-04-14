package com.example.myapp.Membership.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
public class User{

    @Id
    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "hashedPassword", nullable = false, length = 255)
    private String hashedPassword;

    @Column(length = 20)
    private String tier;


    public User() {}

}

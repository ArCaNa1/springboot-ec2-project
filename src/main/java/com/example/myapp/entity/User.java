package com.example.myapp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) //기본 키 생성을 데이터베이스에 위임
    private Long userId;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 255)
    private String hashedPassword;

    @Column(length = 20)
    private String tier;


    public User() {}

    public User(String nickname, String email, String hashedPassword, String tier) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.tier = tier;
    }



}

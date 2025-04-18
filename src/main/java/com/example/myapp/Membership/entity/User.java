package com.example.myapp.Membership.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User{

    @Id
    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(length = 100)
    private String nickname;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "hashedPassword", nullable = false, length = 255)
    private String hashedPassword;

    @Column(length = 20)
    private String tier;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TeamMember> teamMemberships = new ArrayList<>();


    public User() {}

}

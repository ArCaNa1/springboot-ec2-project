package com.example.myapp.Membership.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.myapp.IDE.entity.Chat;
import com.example.myapp.IDE.entity.Quest;
import com.example.myapp.IDE.entity.Submission;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "hashed_password", nullable = false, length = 255)
    private String hashedPassword;

    @Column(name = "tier", length = 20)
    private String tier;

    @Column(name = "last_tier_updated_at")
    private Date lastTierUpdatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TeamMember> teamMemberships = new ArrayList<>();

    @OneToMany(mappedBy = "leaderId")
    private List<Team> ledTeams;

    @OneToMany(mappedBy = "userId")
    private List<TeamMember> teamMembers;

    @OneToMany(mappedBy = "userId")
    private List<Submission> submissions;

    @OneToMany(mappedBy = "creatorId")
    private List<Quest> createdQuests;

    @OneToMany(mappedBy = "userId")
    private List<Chat> chats;
}
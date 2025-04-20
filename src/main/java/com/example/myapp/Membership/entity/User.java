package com.example.myapp.Membership.entity;

<<<<<<< Updated upstream
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
=======
import com.example.myapp.IDE.entity.Chat;
import com.example.myapp.IDE.entity.Quest;
import com.example.myapp.IDE.entity.Submission;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
>>>>>>> Stashed changes
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
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

    @Column(name = "last_tier_updated_at") // 이 부분이 중요
    private Date lastTierUpdatedAt;

<<<<<<< Updated upstream
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TeamMember> teamMemberships = new ArrayList<>();


    public User() {}
=======
>>>>>>> Stashed changes

    @OneToMany(mappedBy = "leaderId")
    private List<Team> ledTeams;

    @OneToMany(mappedBy = "userId")
    private List<TeamMember> teamMembers;

    @OneToMany(mappedBy = "creatorId")
    private List<Quest> createdQuests;

    @OneToMany(mappedBy = "userId")
    private List<Submission> submissions;

    @OneToMany(mappedBy = "userId")
    private List<Chat> chats;
}
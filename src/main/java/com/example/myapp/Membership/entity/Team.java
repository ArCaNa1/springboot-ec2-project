package com.example.myapp.Membership.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.myapp.IDE.entity.Chat;
import com.example.myapp.IDE.entity.Quest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"members", "leaderId"})
@Table(name = "team")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "team_name", nullable = false, length = 50, unique = true)
    private String teamName;

    @Column(name = "team_description", columnDefinition = "TEXT")
    private String teamDescription;

    @Column(name = "max_member", nullable = false)
    private Integer maxMember;

    @Column(name = "current_member_count", columnDefinition = "INT DEFAULT 1")
    private Integer currentMemberCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    @JsonIgnore
    private User leaderId;

    @Column(name = "team_tier", length = 20)
    private String teamTier;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TeamMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "teamId")
    private List<TeamMember> teamMembers;

    @OneToMany(mappedBy = "teamId")
    private List<Quest> quests;

    @OneToMany(mappedBy = "teamId")
    private List<Chat> chats;

    public Team(String teamName, String teamDescription, Integer maxMember, String teamTier, User leaderId) {
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.maxMember = maxMember;
        this.teamTier = teamTier;
        this.leaderId = leaderId;
        this.currentMemberCount = 1;
        this.members = new ArrayList<>();
    }
}
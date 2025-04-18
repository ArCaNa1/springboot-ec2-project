package com.example.myapp.Membership.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties({"members", "leader"})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;

    @Column(nullable = false, unique = true)
    private String teamName;

    @Column(nullable = false)
    private String teamDescription;

    @Column(name = "max_member", nullable = false)
    private int maxmember;

    @Column(nullable = false)
    private int currentMemberCount;

    @Column(nullable = false)
    private String teamTier;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<TeamMember> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    @JsonIgnore
    private User leader;


    // 생성자: 팀 생성 시 사용
    public Team(String teamName, String teamDescription, int maxmember, String teamTier, User leader) {
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.maxmember = maxmember;
        this.teamTier = teamTier;
        this.leader = leader;
        this.currentMemberCount = 1;
        this.members = new ArrayList<>();
    }
}

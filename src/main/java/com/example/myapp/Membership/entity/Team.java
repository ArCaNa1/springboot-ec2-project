package com.example.myapp.Membership.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;

    @Column(nullable = false, unique = true)
    private String teamName;

    @Column(columnDefinition = "TEXT")
    private String teamDescription;

    @Column(nullable = false)
    private int maxmember;

    @Column(nullable = false)
    private int currentMemberCount;

    @Column(nullable = false)
    private String teamTier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private User leader;


    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> members;

}

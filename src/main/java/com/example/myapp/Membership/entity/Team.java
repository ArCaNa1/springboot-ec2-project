package com.example.myapp.Membership.entity;

<<<<<<< Updated upstream
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
=======
import com.example.myapp.IDE.entity.Quest;
import com.example.myapp.IDE.entity.Chat;
>>>>>>> Stashed changes
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties({"members", "leader"})
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< Updated upstream
@NoArgsConstructor
=======
@Table(name = "team")
>>>>>>> Stashed changes
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "team_name", nullable = false, length = 50, unique = true)
    private String teamName;

<<<<<<< Updated upstream
    @Column(nullable = false)
    private String teamDescription;

    @Column(name = "max_member", nullable = false)
    private int maxmember;
=======
    @Column(name = "team_description", columnDefinition = "TEXT")
    private String teamDescription;

    @Column(name = "maxmember", nullable = false)
    private Integer maxMember;
>>>>>>> Stashed changes

    @Column(name = "current_member_count", columnDefinition = "INT DEFAULT 1")
    private Integer currentMemberCount;

    @ManyToOne
    @JoinColumn(name = "leader_id", nullable = false)
    private User leaderId;

    @Column(name = "team_tier", length = 20)
    private String teamTier;

<<<<<<< Updated upstream
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<TeamMember> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    @JsonIgnore
    private User leader;
=======
    @OneToMany(mappedBy = "teamId")
    private List<TeamMember> teamMembers;
>>>>>>> Stashed changes

    @OneToMany(mappedBy = "teamId")
    private List<Quest> quests;

<<<<<<< Updated upstream
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
=======
    @OneToMany(mappedBy = "teamId")
    private List<Chat> chats;
}
>>>>>>> Stashed changes

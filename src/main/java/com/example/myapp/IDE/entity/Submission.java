package com.example.myapp.IDE.entity;

import com.example.myapp.Membership.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submit_id")
    private Integer submitId;

    @ManyToOne
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest questId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "code_context", columnDefinition = "TEXT")
    private String codeContext;

    @Column(name = "output", columnDefinition = "TEXT")
    private String output;

    @CreationTimestamp
    @Column(name = "submitted_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date submittedAt;

    @Column(name = "is_completed", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isCompleted;

    @OneToMany(mappedBy = "submission")
    private List<File> files;

}
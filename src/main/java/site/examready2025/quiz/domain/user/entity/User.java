package site.examready2025.quiz.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import site.examready2025.quiz.domain.quiz.entity.Quiz;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Quiz> createdAuizzes;

    @OneToMany(mappedBy = "responseUser", cascade = CascadeType.ALL)
    private List<Response> responses;

    @Builder
    public User(String name, LocalDateTime createdAt){
        this.name = name;
        this.createdAt = createdAt;
    }
}

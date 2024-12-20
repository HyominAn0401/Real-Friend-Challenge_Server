package site.examready2025.quiz.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.examready2025.quiz.domain.quiz.entity.Quiz;
import site.examready2025.quiz.domain.response.entity.Response;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
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

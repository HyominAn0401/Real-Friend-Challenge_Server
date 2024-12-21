package site.examready2025.quiz.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.examready2025.quiz.domain.quiz.entity.Quiz;
import site.examready2025.quiz.domain.response.entity.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "creator")
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "responseUser")
    private List<Response> responses = new ArrayList<>();

    @Builder
    public User(String name, LocalDateTime createdAt){
        this.name = name;
        this.createdAt = createdAt;
    }
}

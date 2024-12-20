package site.examready2025.quiz.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.examready2025.quiz.domain.question.entity.Question;
import site.examready2025.quiz.domain.response.entity.Response;
import site.examready2025.quiz.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creator;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Response> responses;

    @Builder
    public Quiz(User creator, String title, LocalDateTime createdAt){
        this.creator = creator;
        this.title = title;
        this.createdAt = createdAt;
    }
}

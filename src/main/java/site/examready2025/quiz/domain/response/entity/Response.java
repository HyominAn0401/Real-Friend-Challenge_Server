package site.examready2025.quiz.domain.response.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.examready2025.quiz.domain.answer.entity.Answer;
import site.examready2025.quiz.domain.quiz.entity.Quiz;
import site.examready2025.quiz.domain.user.entity.User;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "response")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "response_user_id", nullable = false)
    private User responseUser;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private boolean isCompleted;

    @Builder
    public Response(Quiz quiz, User responseUser, Integer score, boolean isCompleted) {
        this.quiz = quiz;
        this.responseUser = responseUser;
        this.score = score;
        this.isCompleted = isCompleted;
    }
}

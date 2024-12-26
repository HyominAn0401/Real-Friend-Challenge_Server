package site.examready2025.quiz.domain.choice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.examready2025.quiz.domain.question.entity.Question;
import site.examready2025.quiz.domain.quiz.entity.Quiz;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "choice")
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, length = 50)
    private String answer;

    @Column(nullable = false)
    private boolean isCorrect;

    @Builder
    public Choice(Quiz quiz, Question question, String answer, boolean isCorrect) {
        this.quiz = quiz;
        this.question = question;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
}

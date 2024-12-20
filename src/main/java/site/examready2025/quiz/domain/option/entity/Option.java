package site.examready2025.quiz.domain.option.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.examready2025.quiz.domain.question.entity.Question;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "option")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, length = 50)
    private String answer;

    @Column(nullable = false)
    private Boolean isCorrect;

    @Builder
    public Option(Question question, String answer, Boolean isCorrect) {
        this.question = question;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
}

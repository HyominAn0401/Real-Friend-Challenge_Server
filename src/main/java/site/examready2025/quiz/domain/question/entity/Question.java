package site.examready2025.quiz.domain.question.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.examready2025.quiz.domain.option.entity.Option;
import site.examready2025.quiz.domain.quiz.entity.Quiz;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private Integer order;

    @Column(nullable = false, length = 50)
    private String questionText;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;

    @Builder
    public Question(Quiz quiz, Integer order, String questionText) {
        this.quiz = quiz;
        this.order = order;
        this.questionText = questionText;
    }
}

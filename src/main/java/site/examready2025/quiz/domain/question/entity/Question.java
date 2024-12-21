package site.examready2025.quiz.domain.question.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.examready2025.quiz.domain.choice.entity.Choice;
import site.examready2025.quiz.domain.quiz.entity.Quiz;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String questionText;

//    @OneToMany(mappedBy = "question")
//    private List<Choice> choices = new ArrayList<>();

    @Builder
    public Question(String questionText) {
        this.questionText = questionText;
    }
}

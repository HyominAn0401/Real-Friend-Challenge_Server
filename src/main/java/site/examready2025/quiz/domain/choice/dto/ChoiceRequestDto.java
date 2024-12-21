package site.examready2025.quiz.domain.choice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ChoiceRequestDto {
    private Long quizId;
    private Long questionId;
    private String correctAnswer;
    private List<String> wrongAnswers;
}

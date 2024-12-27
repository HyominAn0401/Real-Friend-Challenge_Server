package site.examready2025.quiz.domain.response.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResultDto {
    private Long questionId;
    private Long selectedChoiceId;
    private Long correctAnswer;
    private boolean isCorrect;
}

package site.examready2025.quiz.domain.question.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponseDto {
    private Long questionId;
    private String questionText;
}

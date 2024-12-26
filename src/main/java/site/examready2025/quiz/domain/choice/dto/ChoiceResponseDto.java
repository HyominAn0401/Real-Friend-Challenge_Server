package site.examready2025.quiz.domain.choice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChoiceResponseDto {

    private Long id;
    private Long questionId;
    private String answer;
    private boolean isCorrect;
}

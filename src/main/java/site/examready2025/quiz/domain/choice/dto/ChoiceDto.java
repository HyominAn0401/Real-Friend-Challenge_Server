package site.examready2025.quiz.domain.choice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChoiceDto {

    private Long id;
    private String answer;
    private boolean correct;
}

package site.examready2025.quiz.domain.choice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChoicesWithQuestionDto {

    private Long questionId;
    private List<ChoiceDto> choiceDtos;
}

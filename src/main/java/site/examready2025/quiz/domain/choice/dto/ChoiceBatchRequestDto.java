package site.examready2025.quiz.domain.choice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ChoiceBatchRequestDto {

    private Long quizId;
    private List<ChoiceRequestDto> choices;
}

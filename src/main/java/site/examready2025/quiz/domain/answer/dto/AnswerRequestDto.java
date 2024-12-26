package site.examready2025.quiz.domain.answer.dto;

import lombok.Getter;

@Getter
public class AnswerRequestDto {

    private Long questionId;
    private Long selectedChoiceId;

}

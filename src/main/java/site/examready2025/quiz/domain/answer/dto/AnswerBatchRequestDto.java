package site.examready2025.quiz.domain.answer.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AnswerBatchRequestDto {
    private Long responseId;
    //private Long quizId;
    private String shareKey;
    private List<AnswerRequestDto> answers;
}

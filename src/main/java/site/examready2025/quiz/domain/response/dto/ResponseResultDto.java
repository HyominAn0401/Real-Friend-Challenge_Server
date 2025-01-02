package site.examready2025.quiz.domain.response.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseResultDto {

//    private Long quizId;
    private Long responseId;
    private List<QuestionResultDto> results;
}

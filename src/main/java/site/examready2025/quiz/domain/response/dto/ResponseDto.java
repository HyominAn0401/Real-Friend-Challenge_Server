package site.examready2025.quiz.domain.response.dto;

import lombok.Getter;
import site.examready2025.quiz.domain.response.entity.Response;

@Getter
public class ResponseDto {

    private Long id;
    private Long quizId;
    private Long responseUserId;
    private int score;
    private boolean isCompleted;

    public ResponseDto(Response response){
        this.id = response.getId();
        this.quizId = response.getQuiz().getId();
        this.responseUserId = response.getResponseUser().getId();
        this.score = response.getScore();
        this.isCompleted = response.isCompleted();
    }
}

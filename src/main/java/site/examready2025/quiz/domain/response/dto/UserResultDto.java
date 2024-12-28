package site.examready2025.quiz.domain.response.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResultDto {
    private String userName;
    private int score;
    private String examNumber;
    private String grade;
}

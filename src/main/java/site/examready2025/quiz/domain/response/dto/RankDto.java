package site.examready2025.quiz.domain.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankDto {
    private Long userId;
    private String userName;
    private Integer score;
    private Integer rank;
}

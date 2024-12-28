package site.examready2025.quiz.domain.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResultDto {
//    private Long quizId;
//    private Integer userRank;
//    private Integer userScore;
    private List<RankDto> rankings;
}


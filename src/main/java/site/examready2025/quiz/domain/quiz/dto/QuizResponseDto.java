package site.examready2025.quiz.domain.quiz.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QuizResponseDto {
    private Long quizId;
    private Long creatorUserId;
    private String title;
    private LocalDateTime createdAt;
}

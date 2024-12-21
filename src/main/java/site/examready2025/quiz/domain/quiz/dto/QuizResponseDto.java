package site.examready2025.quiz.domain.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.examready2025.quiz.domain.choice.dto.ChoiceRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QuizResponseDto {
    private Long quizId;
    private Long creatorUserId;
    private String title;
    private LocalDateTime createdAt;
}

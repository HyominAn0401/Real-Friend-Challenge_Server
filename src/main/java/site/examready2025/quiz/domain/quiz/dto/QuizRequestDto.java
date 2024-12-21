package site.examready2025.quiz.domain.quiz.dto;

import lombok.Builder;
import lombok.Getter;
import site.examready2025.quiz.domain.choice.dto.ChoiceRequestDto;

import java.util.List;

@Getter
public class QuizRequestDto {
    private Long creatorUserId;
}
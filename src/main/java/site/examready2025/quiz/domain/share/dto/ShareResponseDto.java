package site.examready2025.quiz.domain.share.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShareResponseDto {
    private String shareUrl;
}
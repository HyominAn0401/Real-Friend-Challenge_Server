package site.examready2025.quiz.domain.share.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.examready2025.quiz.domain.share.dto.ShareRequestDto;
import site.examready2025.quiz.domain.share.dto.ShareResponseDto;
import site.examready2025.quiz.domain.share.service.ShareService;

@RestController
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;

    @PostMapping("/api/quizzes/share")
    public ResponseEntity<ShareResponseDto> shareQuiz(@RequestBody ShareRequestDto requestDto) {

        ShareResponseDto responseDto = shareService.generateShareUrl(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
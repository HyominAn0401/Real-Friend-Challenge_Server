package site.examready2025.quiz.domain.quiz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.examready2025.quiz.domain.quiz.dto.QuizRequestDto;
import site.examready2025.quiz.domain.quiz.dto.QuizResponseDto;
import site.examready2025.quiz.domain.quiz.service.QuizService;

@RestController
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/api/quizzes")
    public ResponseEntity<QuizResponseDto> createQuiz(@RequestBody QuizRequestDto requestDto) {

        QuizResponseDto responseDto = quizService.createQuiz(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
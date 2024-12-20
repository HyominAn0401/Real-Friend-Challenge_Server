package site.examready2025.quiz.domain.quiz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.examready2025.quiz.domain.quiz.Dto.QuizRequestDto;
import site.examready2025.quiz.domain.quiz.entity.Quiz;
import site.examready2025.quiz.domain.quiz.service.QuizService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/api/quizzes")
    public ResponseEntity<String> createQuiz(@RequestBody QuizRequestDto requestDto) {
        Quiz quiz = quizService.createQuiz(requestDto);
        return ResponseEntity.ok("퀴즈가 생성되었습니다.");
    }
}
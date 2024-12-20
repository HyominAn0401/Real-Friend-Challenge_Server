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
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/api/quizzes")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequestDto requestDto) {
        Quiz quiz = quizService.createQuiz(requestDto);
        return ResponseEntity.ok(quiz);
    }
}
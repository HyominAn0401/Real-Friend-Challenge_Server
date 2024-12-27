package site.examready2025.quiz.domain.quiz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.examready2025.quiz.domain.quiz.dto.QuizRequestDto;
import site.examready2025.quiz.domain.quiz.dto.QuizResponseDto;
import site.examready2025.quiz.domain.quiz.service.QuizService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/api/quizzes")
    public ResponseEntity<QuizResponseDto> createQuiz(@RequestBody QuizRequestDto requestDto) {

        QuizResponseDto responseDto = quizService.createQuiz(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 퀴즈 생성자 이름 조회
    @GetMapping("/api/quizzes/{quizId}/creator")
    public String getQuizCreatorName(@PathVariable("quizId") Long quizId){
        return quizService.getQuizCreatorName(quizId);
    }
}
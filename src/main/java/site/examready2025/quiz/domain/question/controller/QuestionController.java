package site.examready2025.quiz.domain.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.examready2025.quiz.domain.question.dto.QuestionResponseDto;
import site.examready2025.quiz.domain.question.service.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/api/questions")
    public ResponseEntity<List<QuestionResponseDto>> getQuestions(){
        List<QuestionResponseDto> questions = questionService.getQuestions();
        return ResponseEntity.ok(questions);
    }
}

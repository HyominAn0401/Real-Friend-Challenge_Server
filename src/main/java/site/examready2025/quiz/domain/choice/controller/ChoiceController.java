package site.examready2025.quiz.domain.choice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.examready2025.quiz.domain.choice.dto.ChoiceBatchRequestDto;
import site.examready2025.quiz.domain.choice.dto.ChoiceRequestDto;
import site.examready2025.quiz.domain.choice.dto.ChoiceResponseDto;
import site.examready2025.quiz.domain.choice.dto.ChoicesWithQuestionDto;
import site.examready2025.quiz.domain.choice.service.ChoiceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ChoiceController {

    private final ChoiceService choiceService;

//    @PostMapping("/api/choices")
//    public ResponseEntity<String> addChoices(@RequestBody List<ChoiceRequestDto> choiceRequestDtos){
//        choiceService.addChoices(choiceRequestDtos);
//        return ResponseEntity.status(HttpStatus.CREATED).body("보기 저장 완료");
//    }

    @PostMapping("/api/choices")
    public ResponseEntity<String> addChoices(@RequestBody ChoiceBatchRequestDto choiceBatchRequestDto){
        choiceService.addChoices(choiceBatchRequestDto.getQuizId(), choiceBatchRequestDto.getChoices());
        return ResponseEntity.status(HttpStatus.CREATED).body("보기 저장 완료");
    }

    // 퀴즈 id 에 속한 질문별 choice 반환
    @GetMapping("/api/choices/{quizId}")
    public ResponseEntity<List<ChoicesWithQuestionDto>> getChoicesWithQuestionByQuiz(@PathVariable("quizId") Long quizId){
        List<ChoicesWithQuestionDto> choicesWithQuestion = choiceService.getChoicesWithQuestion(quizId);
        return ResponseEntity.ok(choicesWithQuestion);
    }

    // 특정 퀴즈 속한 choice 반환(사용X)
    @GetMapping("/api/choices/v1/{quizId}")
    public ResponseEntity<List<ChoiceResponseDto>> getChoicesByQuiz(@PathVariable("quizId") Long quizId){
        List<ChoiceResponseDto> choices = choiceService.getChoicesByQuiz(quizId);
        return ResponseEntity.ok(choices);
    }

}

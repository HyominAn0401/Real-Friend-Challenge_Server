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
import site.examready2025.quiz.domain.response.entity.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ChoiceController {

    private final ChoiceService choiceService;

    @PostMapping("/api/choices")
    public ResponseEntity<String> addChoices(@RequestBody ChoiceBatchRequestDto choiceBatchRequestDto){
        choiceService.addChoices(choiceBatchRequestDto.getShareKey(), choiceBatchRequestDto.getChoices());
        return ResponseEntity.status(HttpStatus.CREATED).body("보기 저장 완료");
    }

//    @PostMapping("/api/choices")
//    public ResponseEntity<String> addChoices(@RequestBody ChoiceBatchRequestDto choiceBatchRequestDto){
//        choiceService.addChoices(choiceBatchRequestDto.getQuizId(), choiceBatchRequestDto.getChoices());
//        return ResponseEntity.status(HttpStatus.CREATED).body("보기 저장 완료");
//    }

    @GetMapping("/api/choices/{shareKey}")
    public ResponseEntity<List<ChoicesWithQuestionDto>> getChoicesWithQuestionByQuiz(@PathVariable("shareKey") String shareKey){
        List<ChoicesWithQuestionDto> choicesWithQuestion = choiceService.getChoicesWithQuestion(shareKey);
        return ResponseEntity.ok(choicesWithQuestion);
    }

    // 퀴즈 id 에 속한 질문별 choice 반환
//    @GetMapping("/api/choices/{quizId}")
//    public ResponseEntity<List<ChoicesWithQuestionDto>> getChoicesWithQuestionByQuiz(@PathVariable("quizId") Long quizId){
//        List<ChoicesWithQuestionDto> choicesWithQuestion = choiceService.getChoicesWithQuestion(quizId);
//        return ResponseEntity.ok(choicesWithQuestion);
//    }

}

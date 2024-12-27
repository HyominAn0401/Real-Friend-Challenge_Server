package site.examready2025.quiz.domain.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.examready2025.quiz.domain.answer.dto.AnswerBatchRequestDto;
import site.examready2025.quiz.domain.answer.dto.AnswerRequestDto;
import site.examready2025.quiz.domain.answer.service.AnswerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AnswerController {

    private final AnswerService answerService;

//    @PostMapping("/api/answers")
//    public ResponseEntity<String> saveAnswers(@RequestBody AnswerBatchRequestDto requestDto){
//        answerService.saveAnswers(requestDto.getResponseId(), requestDto.getAnswers());
//        return ResponseEntity.status(HttpStatus.CREATED).body("답변 저장 완료");
//    }

    @PostMapping("/api/answers")
    public ResponseEntity<String> saveAnswers(@RequestBody AnswerBatchRequestDto requestDto){
        answerService.saveAnswers(requestDto.getResponseId(), requestDto.getQuizId(), requestDto.getAnswers());

        return ResponseEntity.status(HttpStatus.CREATED).body("답변 저장 완료");
    }


}

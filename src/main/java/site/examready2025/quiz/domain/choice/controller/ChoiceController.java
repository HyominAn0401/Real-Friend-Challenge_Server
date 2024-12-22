package site.examready2025.quiz.domain.choice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.examready2025.quiz.domain.choice.dto.ChoiceBatchRequestDto;
import site.examready2025.quiz.domain.choice.dto.ChoiceRequestDto;
import site.examready2025.quiz.domain.choice.service.ChoiceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
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
}

package site.examready2025.quiz.domain.response.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.examready2025.quiz.domain.response.dto.ResponseDto;
import site.examready2025.quiz.domain.response.dto.ResponseRequestDto;
import site.examready2025.quiz.domain.response.entity.Response;
import site.examready2025.quiz.domain.response.service.ResponseService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ResponseController {

    private final ResponseService responseService;

    // 생성
    @PostMapping("/api/responses")
    public ResponseEntity<ResponseDto> createResponse(@RequestBody ResponseRequestDto responseRequestDto){
        Response response = responseService.createResponse(responseRequestDto.getQuizId(), responseRequestDto.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(response));
    }

    // 제출
    @PostMapping("/api/responses/{responseId}/submit")
    public ResponseEntity<Integer> submitQuiz(@PathVariable("responseId") Long responseId){
        int score = responseService.calculateScore(responseId);
        return ResponseEntity.ok(score);
    }
}

package site.examready2025.quiz.domain.response.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.examready2025.quiz.domain.response.dto.ResultDto;
import site.examready2025.quiz.domain.response.service.ResultService;

@RestController
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/api/result/{quizId}")
    public ResponseEntity<ResultDto> getResults(@PathVariable("quizId") Long quizId, @RequestParam("userId") Long userId){
        ResultDto resultDto = resultService.getResults(quizId, userId);
        return ResponseEntity.ok(resultDto);
    }
}

package site.examready2025.quiz.domain.response.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.examready2025.quiz.domain.response.dto.ResultDto;
import site.examready2025.quiz.domain.response.service.ResultService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/api/result/{shareKey}")
    public ResponseEntity<ResultDto> getResults(@PathVariable("shareKey") String shareKey){
        ResultDto resultDto = resultService.getResults(shareKey);
        return ResponseEntity.ok(resultDto);
    }

//    @GetMapping("/api/result/{quizId}")
//    public ResponseEntity<ResultDto> getResults(@PathVariable("quizId") Long quizId){
//        ResultDto resultDto = resultService.getResults(quizId);
//        return ResponseEntity.ok(resultDto);
//    }
}

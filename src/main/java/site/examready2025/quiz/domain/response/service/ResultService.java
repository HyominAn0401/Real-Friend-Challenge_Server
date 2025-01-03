package site.examready2025.quiz.domain.response.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.examready2025.quiz.domain.quiz.entity.Quiz;
import site.examready2025.quiz.domain.quiz.repository.QuizRepository;
import site.examready2025.quiz.domain.response.dto.RankDto;
import site.examready2025.quiz.domain.response.dto.ResultDto;
import site.examready2025.quiz.domain.response.entity.Response;
import site.examready2025.quiz.domain.response.repository.ResponseRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResultService {

    private final ResponseRepository responseRepository;
    private final QuizRepository quizRepository;

    public ResultDto getResults(String shareKey){
        Quiz quiz = quizRepository.findByShareKey(shareKey).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다."));
        Long quizId = quiz.getId();

        // 완료된 Response만 가져오기
        List<Response> responses = responseRepository.findByQuizIdAndIsCompletedTrue(quizId);

        // 점수 내림차순 정렬
        responses.sort(Comparator
                .comparingInt(Response::getScore).reversed()
                .thenComparing((r1, r2) -> Long.compare(r2.getId(), r1.getId())));

        List<RankDto> rankings = new ArrayList<>();
        int rank = 1;

        for (Response current : responses) {
            rankings.add(new RankDto(
                    current.getResponseUser().getId(),
                    current.getResponseUser().getName(),
                    current.getScore(),
                    rank++
            ));
        }

        return new ResultDto(rankings);
    }

//    public ResultDto getResults(Long quizId) {
//
//        // 완료된 Response만 가져오기
//        List<Response> responses = responseRepository.findByQuizIdAndIsCompletedTrue(quizId);
//
//        // 점수 내림차순
//        responses.sort(Comparator
//                .comparingInt(Response::getScore).reversed()
//                .thenComparing((r1, r2)-> Long.compare(r2.getId(), r1.getId())));
//
//        List<RankDto> rankings = new ArrayList<>();
//        int rank = 1;
//
//        for (int i = 0; i < responses.size(); i++) {
//            Response current = responses.get(i);
//
//            rankings.add(new RankDto(
//                    current.getResponseUser().getId(),
//                    current.getResponseUser().getName(),
//                    current.getScore(),
//                    rank
//            ));
//
//            rank++;
//        }
//
//        return new ResultDto(rankings);
//    }

}

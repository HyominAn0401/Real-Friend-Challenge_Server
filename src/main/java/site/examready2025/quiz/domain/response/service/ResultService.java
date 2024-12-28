package site.examready2025.quiz.domain.response.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public ResultDto getResults(Long quizId) {

        // 완료된 Response만 가져오기
        List<Response> responses = responseRepository.findByQuizIdAndIsCompletedTrue(quizId);

        // 점수 내림차순
        responses.sort(Comparator
                .comparingInt(Response::getScore).reversed()
                .thenComparing((r1, r2)-> Long.compare(r2.getId(), r1.getId())));

        List<RankDto> rankings = new ArrayList<>();
        int rank = 1;

        for (int i = 0; i < responses.size(); i++) {
            Response current = responses.get(i);

            rankings.add(new RankDto(
                    current.getResponseUser().getId(),
                    current.getResponseUser().getName(),
                    current.getScore(),
                    rank
            ));

            rank++;
        }

        return new ResultDto(rankings);
    }

//    public ResultDto getResults(Long quizId, Long userId) {
//
//        List<Response> responses = responseRepository.findByQuizId(quizId);
//
//        responses.sort(Comparator.comparing(Response::getScore).reversed().thenComparing(r -> r.getResponseUser().getName()));
//
//        List<RankDto> rankings = new ArrayList<>();
//        Integer userRank = null;
//        Integer userScore = null;
//        int rank= 1;
//
//        for(int i=0; i<responses.size(); i++){
//            Response current = responses.get(i);
//
//            // 순위 계산
//            if(i>0 && current.getScore()!=responses.get(i-1).getScore())
//                rank = i+1;
//
//            rankings.add(new RankDto(current.getResponseUser().getId(), current.getResponseUser().getName(), current.getScore(), rank));
//
//            if(current.getResponseUser().getId().equals(userId)){
//                userRank = rank;
//                userScore = current.getScore();
//            }
//        }
//
//        return new ResultDto(quizId, userRank, userScore, rankings);
//    }

}

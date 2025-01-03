package site.examready2025.quiz.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.examready2025.quiz.domain.answer.dto.AnswerRequestDto;
import site.examready2025.quiz.domain.answer.entity.Answer;
import site.examready2025.quiz.domain.answer.repository.AnswerRepository;
import site.examready2025.quiz.domain.choice.entity.Choice;
import site.examready2025.quiz.domain.choice.repository.ChoiceRepository;
import site.examready2025.quiz.domain.question.repository.QuestionRepository;
import site.examready2025.quiz.domain.quiz.repository.QuizRepository;
import site.examready2025.quiz.domain.response.entity.Response;
import site.examready2025.quiz.domain.response.repository.ResponseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final QuizRepository quizRepository;


    // 정답 저장
    public void saveAnswers(Long responseId, String shareKey, List<AnswerRequestDto> answerRequestDtos){
        Response response = responseRepository.findById(responseId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈 세션을 찾을 수 없습니다."));

        Long quizId = quizRepository.findByShareKey(shareKey)
                .orElseThrow(() -> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다."))
                .getId();

        for(AnswerRequestDto dto : answerRequestDtos){
            Choice choice = choiceRepository.findByQuizIdAndQuestionIdAndId(quizId, dto.getQuestionId(), dto.getSelectedChoiceId())
                    .orElseThrow(()-> new IllegalArgumentException("quizId로 choice를 찾을 수 없습니다."));

            Answer answer = Answer.builder()
                    .response(response)
                    .question(choice.getQuestion())
                    .selectedChoice(choice)
                    .build();
            answerRepository.save(answer);
        }
    }

//    public void saveAnswers(Long responseId, Long quizId, List<AnswerRequestDto> answerRequestDtos){
//        Response response = responseRepository.findById(responseId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈 세션을 찾을 수 없습니다."));
//
//        for(AnswerRequestDto dto : answerRequestDtos){
//            // quizId, questionId, selectedChoiceId로 Choice를 검색
//            Choice choice= choiceRepository.findByQuizIdAndQuestionIdAndId(quizId, dto.getQuestionId(), dto.getSelectedChoiceId())
//                    .orElseThrow(()-> new IllegalArgumentException("quizId로 Choice를 찾을 수 없습니다."));
//
//            Answer answer = Answer.builder()
//                    .response(response)
//                    .question(choice.getQuestion())
//                    .selectedChoice(choice)
//                    .build();
//            answerRepository.save(answer);
//        }
//    }

}

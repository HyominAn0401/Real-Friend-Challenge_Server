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


    public void saveAnswers(Long responseId, Long quizId, List<AnswerRequestDto> answerRequestDtos){
        Response response = responseRepository.findById(responseId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈 세션을 찾을 수 없습니다."));

        for(AnswerRequestDto dto : answerRequestDtos){
            // quizId, questionId, selectedChoiceId로 Choice를 검색
            Choice choice= choiceRepository.findByQuizIdAndQuestionIdAndId(quizId, dto.getQuestionId(), dto.getSelectedChoiceId())
                    .orElseThrow(()-> new IllegalArgumentException("quizId로 Choice를 찾을 수 없습니다."));

            Answer answer = Answer.builder()
                    .response(response)
                    .question(choice.getQuestion())
                    .selectedChoice(choice)
                    .build();
            answerRepository.save(answer);
        }
    }

//    public void saveAnswers(Long responseId, List<AnswerRequestDto> answerRequestDtos){
//
//        for(AnswerRequestDto dto : answerRequestDtos){
//            Response response = responseRepository.findById(responseId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈 세션을 찾을 수 없습니다. response id : "+responseId));
//            Question question = questionRepository.findById(dto.getQuestionId())
//                    .orElseThrow(()-> new IllegalArgumentException("해당 질문을 찾을 수 없습니다. 질문 id : "+ dto.getQuestionId()));
//
//            Choice choice = choiceRepository.findById(dto.getSelectedChoiceId()).orElseThrow(()-> new IllegalArgumentException("해당 보기를 찾을 수 없습니다. 보기 id : "+dto.getSelectedChoiceId()));
//
//            Answer answer = Answer.builder()
//                    .response(response)
//                    .question(question)
//                    .selectedChoice(choice)
//                    .build();
//            answerRepository.save(answer);
//        }
//    }
}

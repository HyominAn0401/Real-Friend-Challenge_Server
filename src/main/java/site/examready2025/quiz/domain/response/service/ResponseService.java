package site.examready2025.quiz.domain.response.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.examready2025.quiz.domain.answer.entity.Answer;
import site.examready2025.quiz.domain.answer.repository.AnswerRepository;
import site.examready2025.quiz.domain.quiz.entity.Quiz;
import site.examready2025.quiz.domain.quiz.repository.QuizRepository;
import site.examready2025.quiz.domain.quiz.service.QuizService;
import site.examready2025.quiz.domain.response.entity.Response;
import site.examready2025.quiz.domain.response.repository.ResponseRepository;
import site.examready2025.quiz.domain.user.entity.User;
import site.examready2025.quiz.domain.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final QuizService quizService;
    private final UserService userService;
    private final AnswerRepository answerRepository;

    // 퀴즈 세션 생성
    public Response createResponse(Long quizId, String userName){
        User user = userService.createUserEntity(userName);

        Quiz quiz = quizService.getQuizById(quizId);

        Response response = Response.builder()
                .quiz(quiz)
                .responseUser(user)
                .score(0)
                .isCompleted(false)
                .build();

        return responseRepository.save(response);
    }

    // 점수 계산
    @Transactional
    public int calculateScore(Long responseId){
        Response response = responseRepository.findById(responseId).orElseThrow(()-> new IllegalArgumentException("해당 세션을 찾을 수 없습니다. 세션 id : "+responseId));

        List<Answer> answers = answerRepository.findByResponseId(responseId);

        int score = 0;
        for(Answer answer: answers){
            if(answer.getSelectedChoice().isCorrect())
                score++;
        }

        response.setScore(score*10);
        response.setCompleted(true);
        responseRepository.save(response);

        return score*10;
    }
}

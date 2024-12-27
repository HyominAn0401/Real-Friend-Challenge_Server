package site.examready2025.quiz.domain.response.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.examready2025.quiz.domain.answer.entity.Answer;
import site.examready2025.quiz.domain.answer.repository.AnswerRepository;
import site.examready2025.quiz.domain.choice.entity.Choice;
import site.examready2025.quiz.domain.choice.repository.ChoiceRepository;
import site.examready2025.quiz.domain.quiz.entity.Quiz;
import site.examready2025.quiz.domain.quiz.repository.QuizRepository;
import site.examready2025.quiz.domain.quiz.service.QuizService;
import site.examready2025.quiz.domain.response.dto.QuestionResultDto;
import site.examready2025.quiz.domain.response.dto.ResponseResultDto;
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
    private final ChoiceRepository choiceRepository;

    // 퀴즈 생성자 이름 반환
    @Transactional(readOnly = true)
    public String getCreatorName(Long responseId){
        Response response = responseRepository.findById(responseId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈 세션을 찾을 수 없습니다. responseId: "+responseId));
        return response.getQuiz().getCreator().getName();
    }

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

    public ResponseResultDto getResponseResult(Long responseId){
        Response response = responseRepository.findById(responseId).orElseThrow(()->new IllegalArgumentException("해당 퀴즈 세션을 찾을 수 없습니다. response id: "+responseId));

        // 사용자 선택 answer 조회
        List<Answer> answers = answerRepository.findByResponseId(responseId);

        // 결과 리스트
        List<QuestionResultDto> results = answers.stream().map(answer -> {
            Choice selectedChoice = answer.getSelectedChoice();

            //정답 보기
            Choice correctChoice = choiceRepository.findFirstByQuestionIdAndIsCorrectTrue(answer.getQuestion().getId())
                    .orElseThrow(()-> new IllegalArgumentException("해당 보기를 찾을 수 없습니다. questionid : "+answer.getQuestion().getId()));

            boolean isCorrect = selectedChoice != null && selectedChoice.isCorrect();

            return QuestionResultDto.builder()
                    .questionId(answer.getQuestion().getId())
                    .selectedChoiceId(selectedChoice!=null ? selectedChoice.getId() : null)
                    .correctAnswer(correctChoice.getId())
                    .isCorrect(isCorrect)
                    .build();
        }).toList();

        return ResponseResultDto.builder()
                .quizId(response.getQuiz().getId())
                .responseId(response.getId())
                .results(results)
                .build();
    }
}

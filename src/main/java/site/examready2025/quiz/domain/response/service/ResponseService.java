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
import site.examready2025.quiz.domain.response.dto.UserResultDto;
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
    private final ResultService resultService;

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

    // 특정 퀴즈를 푼 사용자의 퀴즈 정보 조회
    // Quiz 풀이 완료 시 Response 업데이트
    @Transactional
    public UserResultDto getUserQuizResult(Long responseId){
        Response response = responseRepository.findById(responseId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈 세션을 찾을 수 없습니다. responseId: "+responseId));

        List<Answer> answers = answerRepository.findByResponseId(responseId);

        int score = calculateScoreV2(answers);

        // Response 업데이트
        response.setScore(score);
        response.setCompleted(true);
        responseRepository.save(response);

        User user = response.getResponseUser();
        String examNumber = generateExamNumber(user);
        String grade = calculateGrade(score);

        return UserResultDto.builder()
                .userName(user.getName())
                .score(score)
                .examNumber(examNumber)
                .grade(grade)
                .build();
    }

    // 등급 계산
    public String calculateGrade(int score){
        if(score>=100)  return "1";
        else if(score >=90) return "2";
        else if(score>=80)  return "3";
        else if(score>=60)  return "4";
        else if(score>=40)  return "5";
        else if(score>=30)  return "6";
        else if(score>=20)  return "7";
        else if(score>=10)  return "8";
        else return "9";
    }

    // 수험번호 생성
    public String generateExamNumber(User user){
        String creationNano = String.format("%06d", user.getCreatedAt().getNano()%1000000);
        return creationNano + user.getId();
    }

    // 점수 계산 V2
    public int calculateScoreV2(List<Answer> answers){
        int score = 0;
        for(Answer answer: answers){
            if(answer.getSelectedChoice().isCorrect()){
                score += 10;
            }
        }
        return score;
    }

    // 점수 계산(사용X)
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

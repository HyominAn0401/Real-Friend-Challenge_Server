package site.examready2025.quiz.domain.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.examready2025.quiz.domain.choice.dto.ChoiceRequestDto;
import site.examready2025.quiz.domain.choice.entity.Choice;
import site.examready2025.quiz.domain.choice.repository.ChoiceRepository;
import site.examready2025.quiz.domain.question.entity.Question;
import site.examready2025.quiz.domain.question.repository.QuestionRepository;
import site.examready2025.quiz.domain.quiz.dto.QuizRequestDto;
import site.examready2025.quiz.domain.quiz.dto.QuizResponseDto;
import site.examready2025.quiz.domain.quiz.entity.Quiz;
import site.examready2025.quiz.domain.quiz.repository.QuizRepository;
import site.examready2025.quiz.domain.user.entity.User;
import site.examready2025.quiz.domain.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    public QuizResponseDto createQuiz(QuizRequestDto requestDto) {
        // 사용자(생성자) 확인
        User creator = userRepository.findById(requestDto.getCreatorUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id: "+requestDto.getCreatorUserId()));

        // 퀴즈 제목 생성
        String title = "quiz-" + LocalDateTime.now().toLocalDate() + "-" + creator.getId();

        // 퀴즈 생성
        Quiz quiz = Quiz.builder()
                .creator(creator)
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();
        quizRepository.save(quiz);

        // 질문별 정답, 오답 저장
        for(ChoiceRequestDto choiceRequestDto : requestDto.getChoices()){
            Question question = questionRepository.findById(choiceRequestDto.getQuestionId()).orElseThrow(()-> new IllegalArgumentException("질문을 찾을 수 없습니다."));

            // 정답
            Choice correct = Choice.builder()
                    .question(question)
                    .answer(choiceRequestDto.getCorrectAnswer())
                    .isCorrect(true)
                    .build();
            choiceRepository.save(correct);

            // 오답
            for(String wrongAnswer : choiceRequestDto.getWrongAnswers()){
                Choice wrong = Choice.builder()
                        .question(question)
                        .answer(wrongAnswer)
                        .isCorrect(false)
                        .build();
                choiceRepository.save(wrong);
            }
        }

        return QuizResponseDto.builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .createdAt(quiz.getCreatedAt())
                .creatorUserId(creator.getId())
                .build();
    }
}
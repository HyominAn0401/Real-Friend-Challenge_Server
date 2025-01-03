package site.examready2025.quiz.domain.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.examready2025.quiz.domain.choice.service.ChoiceService;
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

        return mapperToResponseDto(quiz);
    }

    // id기반 퀴즈 조회
    public Quiz getQuizById(Long quizId){
        return quizRepository.findById(quizId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다. 퀴즈 id : "+quizId));
    }

    // shareKey 기반 퀴즈 조회
    @Transactional(readOnly = true)
    public QuizResponseDto getQuizByShareKey(String shareKey){
        Quiz quiz = quizRepository.findByShareKey(shareKey).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다."));

        return mapperToResponseDto(quiz);
    }

    // id 기반 퀴즈 생성자 조회
    @Transactional(readOnly = true)
    public String getQuizCreatorName(Long quizId){
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다. 퀴즈 id: "+ quizId));
        return quiz.getCreator().getName();
    }

    // shareKey 기반 퀴즈 생성자 조회
    public String getQuizCreatorNameByShareKey(String shareKey){
        Quiz quiz = quizRepository.findByShareKey(shareKey).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다."));

        return quiz.getCreator().getName();
    }

    // quiz -> QuizResponseDto
    private QuizResponseDto mapperToResponseDto(Quiz quiz){
        return QuizResponseDto.builder()
                .title(quiz.getTitle())
                .createdAt(quiz.getCreatedAt())
                .creatorUserId(quiz.getCreator().getId())
                .shareKey(quiz.getShareKey())
                .build();
    }
}
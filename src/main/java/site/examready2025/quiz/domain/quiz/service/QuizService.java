package site.examready2025.quiz.domain.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.examready2025.quiz.domain.quiz.Dto.QuizRequestDto;
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

    public Quiz createQuiz(QuizRequestDto requestDto) {
        User creator = userRepository.findById(requestDto.getCreatorUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String title = "quiz-" + LocalDateTime.now().toLocalDate() + "-" + creator.getId();

        Quiz quiz = Quiz.builder()
                .creator(creator)
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();

        return quizRepository.save(quiz);
    }
}
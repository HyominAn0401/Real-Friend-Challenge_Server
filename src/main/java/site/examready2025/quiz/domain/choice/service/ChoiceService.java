package site.examready2025.quiz.domain.choice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.examready2025.quiz.domain.choice.dto.ChoiceDto;
import site.examready2025.quiz.domain.choice.dto.ChoiceRequestDto;
import site.examready2025.quiz.domain.choice.dto.ChoiceResponseDto;
import site.examready2025.quiz.domain.choice.dto.ChoicesWithQuestionDto;
import site.examready2025.quiz.domain.choice.entity.Choice;
import site.examready2025.quiz.domain.choice.repository.ChoiceRepository;
import site.examready2025.quiz.domain.question.entity.Question;
import site.examready2025.quiz.domain.question.repository.QuestionRepository;
import site.examready2025.quiz.domain.quiz.entity.Quiz;
import site.examready2025.quiz.domain.quiz.repository.QuizRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChoiceService {

    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final QuizRepository quizRepository;

    public List<ChoiceResponseDto> getChoicesByQuiz(Long quizId){
        List<Choice> choices = choiceRepository.findByQuizId(quizId);
        return choices.stream().map(choice -> new ChoiceResponseDto(choice.getId(), choice.getQuestion().getId(), choice.getAnswer(), choice.isCorrect())).toList();
    }

    public void addChoices(Long quizId, List<ChoiceRequestDto> choiceRequestDtos){

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다. 퀴즈 id : "+quizId));
        for(ChoiceRequestDto dto : choiceRequestDtos){
            //Quiz quiz = quizRepository.findById(dto.getQuizId()).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다. 퀴즈 id: "+dto.getQuizId()));

            Question question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(()-> new IllegalArgumentException("해당 질문을 찾을 수 없습니다. 질문 Id : "+dto.getQuestionId()));

            // 정답
            Choice correct = Choice.builder()
                    .question(question)
                    .quiz(quiz)
                    .answer(dto.getCorrectAnswer())
                    .isCorrect(true)
                    .build();
            choiceRepository.save(correct);

            // 오답
            for(String wrongAnswers : dto.getWrongAnswers()){
                Choice wrong = Choice.builder()
                        .quiz(quiz)
                        .question(question)
                        .answer(wrongAnswers)
                        .isCorrect(false)
                        .build();
                choiceRepository.save(wrong);
            }
        }
    }

    // 보기 데이터 반환
    public List<ChoicesWithQuestionDto> getChoicesWithQuestion(Long quizId){
        List<Choice> choices = choiceRepository.findByQuizId(quizId);
        Map<Long, List<ChoiceDto>> groupedChoices = choices.stream()
                .collect(Collectors.groupingBy(
                        choice -> choice.getQuestion().getId(),
                        Collectors.mapping(
                                choice -> new ChoiceDto(choice.getId(), choice.getAnswer(), choice.isCorrect()), // DTO 변환
                                Collectors.toList()
                        )
                ));
        return groupedChoices.entrySet().stream()
                .map(entry -> new ChoicesWithQuestionDto(entry.getKey(), entry.getValue()))
                .toList();
    }
}

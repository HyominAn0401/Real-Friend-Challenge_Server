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

import java.util.ArrayList;
import java.util.Collections;
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

    // 특정 퀴즈 보기 반환
    @Transactional(readOnly = true)
    public List<ChoiceResponseDto> getChoicesByQuiz(String shareKey){
        List<Choice> choices = choiceRepository.findByQuizShareKey(shareKey);
        return choices.stream().map(choice-> new ChoiceResponseDto(choice.getId(), choice.getQuestion().getId(), choice.getAnswer(), choice.isCorrect())).toList();
    }

    // 퀴즈에 보기 추가
    public void addChoices(String shareKey, List<ChoiceRequestDto> choiceRequestDtos){
        Quiz quiz = quizRepository.findByShareKey(shareKey).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다."));

        for(ChoiceRequestDto dto : choiceRequestDtos){
            Question question = questionRepository.findById(dto.getQuestionId()).orElseThrow(()-> new IllegalArgumentException("해당 질문을 찾을 수 없습니다."));

            List<Choice> choices = new ArrayList<>();

            // 정답 추가
            choices.add(Choice.builder().quiz(quiz).question(question).answer(dto.getCorrectAnswer()).isCorrect(true).build());

            // 오답 추가
            for(String wrongAnswer : dto.getWrongAnswers()){
                choices.add(Choice.builder().quiz(quiz).question(question).answer(wrongAnswer).isCorrect(false).build());
            }

            Collections.shuffle(choices);

            choices.forEach(choiceRepository::save);
        }
    }

    // share Key 기반 보기 데이터 반환
    @Transactional(readOnly = true)
    public List<ChoicesWithQuestionDto> getChoicesWithQuestion(String shareKey){
        Quiz quiz = quizRepository.findByShareKey(shareKey).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다."));

        List<Choice> choices = choiceRepository.findByQuizId(quiz.getId());

        Map<Long, List<ChoiceDto>> groupedChoices = choices.stream()
                .collect(Collectors.groupingBy(
                        choice -> choice.getQuestion().getId(),
                        Collectors.mapping(
                                choice -> new ChoiceDto(choice.getId(), choice.getAnswer(), choice.isCorrect()), // DTO 변환
                                Collectors.toList()
                        )
                ));

        return groupedChoices.entrySet().stream().map(entry-> new ChoicesWithQuestionDto(entry.getKey(), entry.getValue())).toList();
    }

//    public List<ChoiceResponseDto> getChoicesByQuiz(Long quizId){
//        List<Choice> choices = choiceRepository.findByQuizId(quizId);
//        return choices.stream().map(choice -> new ChoiceResponseDto(choice.getId(), choice.getQuestion().getId(), choice.getAnswer(), choice.isCorrect())).toList();
//    }
//
//    public void addChoices(Long quizId, List<ChoiceRequestDto> choiceRequestDtos){
//        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다. 퀴즈id : "+quizId));
//
//        //Quiz quiz = quizRepository.findByShareKey(shareKey).orElseThrow(()-> new IllegalArgumentException("해당 퀴즈를 찾을 수 없습니다. 퀴즈 shareKey: "+shareKey));
//
//        for(ChoiceRequestDto dto : choiceRequestDtos){
//            Question question = questionRepository.findById(dto.getQuestionId()).orElseThrow(()-> new IllegalArgumentException("해당 질문을 찾을 수 없습니다. questionId: "+dto.getQuestionId()));
//
//            List<Choice> choices = new ArrayList<>();
//
//            choices.add(Choice.builder().question(question)
//                    .quiz(quiz).answer(dto.getCorrectAnswer()).isCorrect(true).build());
//
//            for(String wrongAnswer : dto.getWrongAnswers()){
//                choices.add(Choice.builder().quiz(quiz).question(question).answer(wrongAnswer).isCorrect(false).build());
//            }
//
//            Collections.shuffle(choices);
//
//            for(Choice choice : choices){
//                choiceRepository.save(choice);
//            }
//        }
//    }
//
//    // 보기 데이터 반환
//    public List<ChoicesWithQuestionDto> getChoicesWithQuestion(Long quizId){
//        List<Choice> choices = choiceRepository.findByQuizId(quizId);
//        Map<Long, List<ChoiceDto>> groupedChoices = choices.stream()
//                .collect(Collectors.groupingBy(
//                        choice -> choice.getQuestion().getId(),
//                        Collectors.mapping(
//                                choice -> new ChoiceDto(choice.getId(), choice.getAnswer(), choice.isCorrect()), // DTO 변환
//                                Collectors.toList()
//                        )
//                ));
//        return groupedChoices.entrySet().stream()
//                .map(entry -> new ChoicesWithQuestionDto(entry.getKey(), entry.getValue()))
//                .toList();
//    }
}

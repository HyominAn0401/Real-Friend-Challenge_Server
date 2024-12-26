package site.examready2025.quiz.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.examready2025.quiz.domain.question.dto.QuestionResponseDto;
import site.examready2025.quiz.domain.question.entity.Question;
import site.examready2025.quiz.domain.question.repository.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 질문 리스트
    public List<QuestionResponseDto> getQuestions(){
        List<Question> questions = questionRepository.findAll();

        if(questions.isEmpty()){
            throw new IllegalStateException("질문이 존재하지 않습니다.");
        }

        return questions.stream().map(question -> QuestionResponseDto.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .build())
                .collect(Collectors.toList());
    }
}

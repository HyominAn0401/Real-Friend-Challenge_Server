package site.examready2025.quiz.domain.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.examready2025.quiz.domain.answer.entity.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByResponseId(Long responseId);
}

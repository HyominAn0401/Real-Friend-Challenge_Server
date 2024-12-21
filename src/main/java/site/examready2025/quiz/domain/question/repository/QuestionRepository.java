package site.examready2025.quiz.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.examready2025.quiz.domain.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}

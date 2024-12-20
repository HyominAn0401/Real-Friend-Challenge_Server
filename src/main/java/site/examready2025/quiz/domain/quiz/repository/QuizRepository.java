package site.examready2025.quiz.domain.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.examready2025.quiz.domain.quiz.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
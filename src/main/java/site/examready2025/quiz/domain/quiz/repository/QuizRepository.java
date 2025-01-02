package site.examready2025.quiz.domain.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.examready2025.quiz.domain.quiz.entity.Quiz;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByShareKey(String shareKey);
}
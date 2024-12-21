package site.examready2025.quiz.domain.choice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.examready2025.quiz.domain.choice.entity.Choice;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}

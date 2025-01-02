package site.examready2025.quiz.domain.choice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.examready2025.quiz.domain.choice.entity.Choice;
import site.examready2025.quiz.domain.quiz.entity.Quiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    Optional<Choice> findByQuizIdAndQuestionIdAndId(Long quizId, Long questionId, Long choiceId);

    List<Choice> findByQuizId(Long quizId);

    Optional<Choice> findFirstByQuestionIdAndIsCorrectTrue(Long questionId);

    // shareKey 기반 조회
    Optional<Choice> findByQuizShareKeyAndQuestionIdAndId(String shareKey, Long questionId, Long choiceId);
    List<Choice> findByQuizShareKey(String shareKey);

}

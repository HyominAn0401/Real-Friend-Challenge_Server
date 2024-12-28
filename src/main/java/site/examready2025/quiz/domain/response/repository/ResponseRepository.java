package site.examready2025.quiz.domain.response.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.examready2025.quiz.domain.response.entity.Response;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

    List<Response> findByQuizId(Long quizId);

    List<Response> findByQuizIdAndIsCompletedTrue(Long quizId);
}

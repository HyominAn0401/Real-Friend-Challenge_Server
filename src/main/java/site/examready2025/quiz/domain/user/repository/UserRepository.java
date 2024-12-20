package site.examready2025.quiz.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.examready2025.quiz.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

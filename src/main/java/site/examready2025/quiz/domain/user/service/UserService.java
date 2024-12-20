package site.examready2025.quiz.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.examready2025.quiz.domain.user.Dto.UserRequestDto;
import site.examready2025.quiz.domain.user.entity.User;
import site.examready2025.quiz.domain.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserRequestDto userRequestDto){
        User user = User.builder().name(userRequestDto.getName())
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }
}

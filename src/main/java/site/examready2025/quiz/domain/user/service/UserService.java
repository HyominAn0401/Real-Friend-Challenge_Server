package site.examready2025.quiz.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.examready2025.quiz.domain.user.dto.UserRequestDto;
import site.examready2025.quiz.domain.user.dto.UserResponseDto;
import site.examready2025.quiz.domain.user.entity.User;
import site.examready2025.quiz.domain.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto userRequestDto){
        User user = User.builder()
                .name(userRequestDto.getName())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

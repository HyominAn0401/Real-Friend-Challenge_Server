package site.examready2025.quiz.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.examready2025.quiz.domain.user.Dto.UserRequestDto;
import site.examready2025.quiz.domain.user.entity.User;
import site.examready2025.quiz.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequestDto userRequestDto){
        User user = userService.createUser(userRequestDto);
        return ResponseEntity.ok("사용자가 생성되었습니다.");
    }
}

package com.example.schedule2.controller;

import com.example.schedule2.dto.UserRequestDto;
import com.example.schedule2.dto.UserResponseDto;
import com.example.schedule2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 생성
    @PostMapping
    public UserResponseDto createUser(
            @Valid @RequestBody UserRequestDto requestDto) {

        return userService.createUser(requestDto);
    }

    // 회원 전체 조회
    @GetMapping
    public List<UserResponseDto> getUsers() {

        return userService.getUsers();
    }

    // 회원 단건 조회
    @GetMapping("/{id}")
    public UserResponseDto getUser(
            @PathVariable Long id) {

        return userService.getUser(id);
    }

    // 회원 수정
    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto requestDto) {

        return userService.updateUser(id, requestDto);
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return "삭제되었습니다.";
    }
}
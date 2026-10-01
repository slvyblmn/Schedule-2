package com.example.schedule2.controller;

import com.example.schedule2.dto.LoginRequestDto;
import com.example.schedule2.entity.User;
import com.example.schedule2.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpSession session) {

        User user = userService.login(requestDto);

        session.setAttribute("userId", user.getId());

        return "로그인 성공";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "로그아웃 성공";
    }
}
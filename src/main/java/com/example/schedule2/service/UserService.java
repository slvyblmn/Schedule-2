package com.example.schedule2.service;

import com.example.schedule2.dto.LoginRequestDto;
import com.example.schedule2.dto.UserRequestDto;
import com.example.schedule2.dto.UserResponseDto;
import com.example.schedule2.entity.User;
import com.example.schedule2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원 생성
    public UserResponseDto createUser(UserRequestDto requestDto) {

        User user = new User(
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        userRepository.save(user);

        return new UserResponseDto(user);
    }

    // 회원 전체 조회
    public List<UserResponseDto> getUsers() {

        List<User> users = userRepository.findAll();

        List<UserResponseDto> result = new ArrayList<>();

        for (User user : users) {
            result.add(new UserResponseDto(user));
        }

        return result;
    }

    // 회원 단건 조회
    public UserResponseDto getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        return new UserResponseDto(user);
    }

    // 회원 수정
    public UserResponseDto updateUser(
            Long id,
            UserRequestDto requestDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        user.update(
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        userRepository.save(user);

        return new UserResponseDto(user);
    }

    // 회원 삭제
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        userRepository.delete(user);
    }

    // 로그인
    public User login(LoginRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 틀렸습니다."));

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 틀렸습니다.");
        }

        return user;
    }
}
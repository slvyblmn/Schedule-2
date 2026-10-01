package com.example.schedule2.controller;

import com.example.schedule2.dto.ScheduleRequestDto;
import com.example.schedule2.dto.ScheduleResponseDto;
import com.example.schedule2.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성
    @PostMapping
    public ScheduleResponseDto createSchedule(
            @Valid @RequestBody ScheduleRequestDto requestDto,
            HttpSession session) {

        checkLogin(session);

        return scheduleService.createSchedule(requestDto);
    }

    // 일정 전체 조회
    @GetMapping
    public List<ScheduleResponseDto> getSchedules(
            HttpSession session) {

        checkLogin(session);

        return scheduleService.getSchedules();
    }

    // 일정 단건 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(
            @PathVariable Long id,
            HttpSession session) {

        checkLogin(session);

        return scheduleService.getSchedule(id);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequestDto requestDto,
            HttpSession session) {

        checkLogin(session);

        return scheduleService.updateSchedule(id, requestDto);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public String deleteSchedule(
            @PathVariable Long id,
            HttpSession session) {

        checkLogin(session);

        scheduleService.deleteSchedule(id);

        return "삭제되었습니다.";
    }

    // 로그인 확인
    private void checkLogin(HttpSession session) {

        if (session.getAttribute("userId") == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
    }
}
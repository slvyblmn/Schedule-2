package com.example.schedule2.service;
import com.example.schedule2.dto.ScheduleRequestDto;
import com.example.schedule2.dto.ScheduleResponseDto;
import com.example.schedule2.entity.Schedule;
import com.example.schedule2.entity.User;
import com.example.schedule2.repository.ScheduleRepository;
import com.example.schedule2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            UserRepository userRepository) {

        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public ScheduleResponseDto createSchedule(
            ScheduleRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        Schedule schedule = new Schedule(
                user,
                requestDto.getTitle(),
                requestDto.getContent()
        );

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    public List<ScheduleResponseDto> getSchedules() {

        List<Schedule> schedules = scheduleRepository.findAll();

        List<ScheduleResponseDto> result = new ArrayList<>();

        for (Schedule schedule : schedules) {
            result.add(new ScheduleResponseDto(schedule));
        }

        return result;
    }

    public ScheduleResponseDto getSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정이 없습니다."));

        return new ScheduleResponseDto(schedule);
    }

    public ScheduleResponseDto updateSchedule(
            Long id,
            ScheduleRequestDto requestDto) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정이 없습니다."));

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        schedule.update(
                user,
                requestDto.getTitle(),
                requestDto.getContent()
        );

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    public void deleteSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정이 없습니다."));

        scheduleRepository.delete(schedule);
    }
}
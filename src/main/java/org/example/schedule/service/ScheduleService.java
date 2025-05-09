package org.example.schedule.service;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> findAllSchedules();

    ScheduleResponseDto findScheduleById(Long id);

    List<ScheduleResponseDto> searchSchedules(String name, LocalDate updatedAt);

    ScheduleResponseDto updateScheduleById(Long id, ScheduleRequestDto scheduleRequestDto);
}

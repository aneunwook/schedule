package org.example.schedule.repository;

import org.example.schedule.dto.Paging;
import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(Paging paging);

    ScheduleResponseDto findScheduleById(Long id);

    List<ScheduleResponseDto> findSchedulesByParams (String name, LocalDate updatedAt, Long authorId);

    int updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto);

    int deleteSchedule(Long id, String password);
}

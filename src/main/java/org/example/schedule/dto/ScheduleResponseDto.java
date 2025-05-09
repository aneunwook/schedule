package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class ScheduleResponseDto {
    private Long id;

    private String name;
    private String password;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

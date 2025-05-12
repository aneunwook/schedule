package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private Long authorId;
    private String name;
    private String password;
    private String contents;
}

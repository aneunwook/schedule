package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schedule.entity.Author;
import org.example.schedule.entity.Schedule;

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

    private AuthorResponseDto author;

    public ScheduleResponseDto(Schedule schedule, Author author){
        this.id = schedule.getId();
        this.name = schedule.getName();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
        this.author = new AuthorResponseDto(author);
    }
}

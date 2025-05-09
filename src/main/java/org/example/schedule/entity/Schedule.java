package org.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    @Setter
    private Long id;

    private String name;
    private String password;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(String name, String password, String contents){
        this.name = name;
        this.password = password;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}

package org.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Author {
    @Setter
    private Long id;

    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Author(String email, String name){
        this.email = email;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }



}

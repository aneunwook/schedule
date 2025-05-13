package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schedule.entity.Author;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuthorResponseDto {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AuthorResponseDto(Author author){
         this.id = author.getId();
         this.email = author.getEmail();
         this.name = author.getEmail();
         this.createdAt = author.getCreatedAt();
         this.updatedAt = author.getUpdatedAt();
    }
}

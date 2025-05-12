package org.example.schedule.service;


import org.example.schedule.dto.AuthorRequestDto;
import org.example.schedule.dto.AuthorResponseDto;
import org.example.schedule.entity.Schedule;

public interface AuthorService {
    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);

}

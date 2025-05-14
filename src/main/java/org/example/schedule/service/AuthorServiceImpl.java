package org.example.schedule.service;

import org.example.schedule.dto.AuthorRequestDto;
import org.example.schedule.dto.AuthorResponseDto;
import org.example.schedule.entity.Author;
import org.example.schedule.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService{
    public final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * 작성자 생성 하는 메서드
     */
    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {
        Author author = new Author(authorRequestDto.getEmail(), authorRequestDto.getName());

        Author save = authorRepository.save(author);

        return new AuthorResponseDto(save);
    }
}

package org.example.schedule.controller;

import org.example.schedule.dto.AuthorRequestDto;
import org.example.schedule.dto.AuthorResponseDto;
import org.example.schedule.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        return new ResponseEntity<>(authorService.createAuthor(authorRequestDto), HttpStatus.CREATED);
    }

}

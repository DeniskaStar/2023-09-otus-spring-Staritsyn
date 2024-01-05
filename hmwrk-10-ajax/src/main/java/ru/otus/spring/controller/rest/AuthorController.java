package ru.otus.spring.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.service.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("api/v1/authors")
    public ResponseEntity<List<AuthorDto>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }
}

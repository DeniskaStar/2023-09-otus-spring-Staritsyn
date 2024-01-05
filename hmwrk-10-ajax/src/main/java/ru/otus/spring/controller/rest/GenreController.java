package ru.otus.spring.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.service.GenreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreService genreService;

    @GetMapping("api/v1/genres")
    public ResponseEntity<List<GenreDto>> findAll() {
        return ResponseEntity.ok(genreService.findAll());
    }
}

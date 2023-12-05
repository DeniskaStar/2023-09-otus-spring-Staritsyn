package ru.otus.spring.service;

import ru.otus.spring.dto.genre.GenreCreateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.dto.genre.GenreUpdateDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<GenreDto> findAll();

    Optional<GenreDto> findById(String id);

    List<GenreDto> findByIds(Collection<String> genreIds);

    GenreDto create(GenreCreateDto genre);

    GenreDto update(GenreUpdateDto genre);

    void deleteById(String id);
}

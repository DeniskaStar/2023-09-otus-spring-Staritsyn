package ru.otus.spring.service;

import ru.otus.spring.dto.genre.GenreDto;

import java.util.Collection;
import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();

    List<GenreDto> findByIds(Collection<Long> genreIds);

    GenreDto create(GenreDto genreDto);
}

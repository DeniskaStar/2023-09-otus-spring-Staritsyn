package ru.otus.spring.service;

import ru.otus.spring.dto.genre.GenreCreateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.dto.genre.GenreUpdateDto;

import java.util.Collection;
import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();

    GenreDto findById(long id);

    List<GenreDto> findByIds(Collection<Long> genreIds);

    GenreDto create(GenreCreateDto genreCreateDto);

    GenreDto update(GenreUpdateDto genreUpdateDto);

    void deleteById(long id);
}

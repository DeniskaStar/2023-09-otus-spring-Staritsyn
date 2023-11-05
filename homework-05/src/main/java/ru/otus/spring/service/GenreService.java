package ru.otus.spring.service;

import ru.otus.spring.dto.genre.GenreModel;

import java.util.Collection;
import java.util.List;

public interface GenreService {

    List<GenreModel> findAll();

    List<GenreModel> findByIds(Collection<Long> genreIds);

    GenreModel save(GenreModel genreModel);
}

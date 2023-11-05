package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.GenreMapper;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.data.repository.GenreRepository;
import ru.otus.spring.dto.genre.GenreModel;
import ru.otus.spring.service.GenreService;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GenreModel> findAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toModel)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreModel> findByIds(Collection<Long> genreIds) {
        return genreRepository.findByIds(genreIds).stream()
                .map(genreMapper::toModel)
                .toList();
    }

    @Override
    @Transactional
    public GenreModel save(GenreModel genreModel) {
        var savedGenre = genreRepository.save(prepareGenre(genreModel));
        return genreMapper.toModel(savedGenre);
    }

    private Genre prepareGenre(GenreModel genreModel) {
        return genreMapper.toDao(genreModel);
    }
}

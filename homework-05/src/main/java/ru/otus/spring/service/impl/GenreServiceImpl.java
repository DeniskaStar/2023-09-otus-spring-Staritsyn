package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.GenreMapper;
import ru.otus.spring.data.repository.GenreRepository;
import ru.otus.spring.dto.genre.GenreDto;
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
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findByIds(Collection<Long> genreIds) {
        return genreRepository.findByIds(genreIds).stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public GenreDto create(GenreDto genreDto) {
        return genreMapper.toDto(genreRepository.save(genreMapper.toEntity(genreDto)));
    }
}

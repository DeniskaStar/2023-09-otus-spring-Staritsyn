package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.GenreMapper;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.data.repository.GenreRepository;
import ru.otus.spring.dto.genre.GenreCreateEditDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.GenreService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public Optional<GenreDto> findById(String id) {
        return genreRepository.findById(id)
                .map(genreMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findByIds(Collection<String> genreIds) {
        return genreRepository.findByIdIn(genreIds).stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public GenreDto create(GenreCreateEditDto genreCreateDto) {
        Genre genre = genreMapper.toEntity(genreCreateDto);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public GenreDto update(String id, GenreCreateEditDto genreUpdateDto) {
        return genreRepository.findById(id)
                .map(existGenre -> genreMapper.toEntity(genreUpdateDto, existGenre))
                .map(genreRepository::save)
                .map(genreMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Genre [id: %s] not found".formatted(id)));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        genreRepository.deleteById(id);
    }
}

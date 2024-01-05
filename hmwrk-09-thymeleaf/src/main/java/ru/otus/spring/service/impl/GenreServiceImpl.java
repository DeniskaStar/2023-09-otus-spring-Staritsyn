package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.GenreMapper;
import ru.otus.spring.data.repository.GenreRepository;
import ru.otus.spring.dto.genre.GenreCreateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.dto.genre.GenreUpdateDto;
import ru.otus.spring.exception.NotFoundException;
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
    public GenreDto findById(long id) {
        return genreRepository.findById(id)
                .map(genreMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Genre [id: %d] not found".formatted(id)));
    }


    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findByIds(Collection<Long> genreIds) {
        return genreRepository.findByIdIn(genreIds).stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public GenreDto create(GenreCreateDto genreCreateDto) {
        var genre = genreMapper.toEntity(genreCreateDto);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public GenreDto update(GenreUpdateDto genreUpdateDto) {
        var existingGenre = genreRepository.findById(genreUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("Genre [id: %d] not found"
                        .formatted(genreUpdateDto.getId())));
        genreMapper.copy(genreUpdateDto, existingGenre);
        return genreMapper.toDto(existingGenre);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }
}

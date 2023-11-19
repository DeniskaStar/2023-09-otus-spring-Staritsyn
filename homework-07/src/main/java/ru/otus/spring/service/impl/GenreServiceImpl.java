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
    public Optional<GenreDto> findById(long id) {
        return genreRepository.findById(id)
                .map(genreMapper::toDto);
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
        var genre = genreMapper.toEntity(GenreDto.of(genreCreateDto));
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public GenreDto update(GenreUpdateDto genreUpdateDto) {
        var genre = genreRepository.findById(genreUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("Genre [id: %d] not found"
                        .formatted(genreUpdateDto.getId())));
        genre.setName(genreUpdateDto.getName());
        return genreMapper.toDto(genre);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }
}

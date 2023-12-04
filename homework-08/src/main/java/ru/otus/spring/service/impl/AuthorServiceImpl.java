package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.AuthorMapper;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.repository.AuthorRepository;
import ru.otus.spring.dto.author.AuthorCreateEditDto;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> findById(String id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto);
    }

    @Override
    @Transactional
    public AuthorDto create(AuthorCreateEditDto authorCreateDto) {
        Author author = authorMapper.toEntity(authorCreateDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public AuthorDto update(String id, AuthorCreateEditDto authorUpdateDto) {
        return authorRepository.findById(id)
                .map(existAuthor -> authorMapper.toEntity(authorUpdateDto, existAuthor))
                .map(authorRepository::save)
                .map(authorMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Author [id: %s] not found".formatted(id)));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }
}

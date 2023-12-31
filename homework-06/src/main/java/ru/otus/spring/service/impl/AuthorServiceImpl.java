package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.AuthorMapper;
import ru.otus.spring.data.repository.AuthorRepository;
import ru.otus.spring.dto.author.AuthorCreateDto;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.author.AuthorUpdateDto;
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
    public Optional<AuthorDto> findById(long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto);
    }

    @Override
    @Transactional
    public AuthorDto create(AuthorCreateDto authorCreateDto) {
        var author = authorMapper.toEntity(AuthorDto.of(authorCreateDto));
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public AuthorDto update(AuthorUpdateDto authorUpdateDto) {
        var author = authorRepository.findById(authorUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("Author [id: %d] not found"
                        .formatted(authorUpdateDto.getId())));
        author.setFullName(authorUpdateDto.getFullName());
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }
}

package ru.otus.spring.service;

import ru.otus.spring.dto.author.AuthorCreateDto;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.author.AuthorUpdateDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorDto> findAll();

    Optional<AuthorDto> findById(long id);

    AuthorDto create(AuthorCreateDto authorDto);

    AuthorDto update(AuthorUpdateDto author);

    void deleteById(long id);
}

package ru.otus.spring.service;

import ru.otus.spring.dto.author.AuthorCreateEditDto;
import ru.otus.spring.dto.author.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorDto> findAll();

    Optional<AuthorDto> findById(String id);

    AuthorDto create(AuthorCreateEditDto authorDto);

    AuthorDto update(String id, AuthorCreateEditDto author);

    void deleteById(String id);
}

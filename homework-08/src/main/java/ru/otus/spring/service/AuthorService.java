package ru.otus.spring.service;

import ru.otus.spring.dto.author.AuthorCreateDto;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.author.AuthorUpdateDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorDto> findAll();

    Optional<AuthorDto> findById(String id);

    AuthorDto create(AuthorCreateDto author);

    AuthorDto update(AuthorUpdateDto author);

    void deleteById(String id);
}

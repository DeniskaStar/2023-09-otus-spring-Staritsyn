package ru.otus.spring.service;

import ru.otus.spring.dto.author.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorDto> findAll();

    Optional<AuthorDto> findById(long id);

    AuthorDto create(AuthorDto authorDto);
}

package ru.otus.spring.service;

import ru.otus.spring.dto.author.AuthorCreateDto;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.author.AuthorUpdateDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();

    AuthorDto findById(long id);

    AuthorDto create(AuthorCreateDto authorCreateDto);

    AuthorDto update(AuthorUpdateDto authorUpdateDto);

    void deleteById(long id);
}

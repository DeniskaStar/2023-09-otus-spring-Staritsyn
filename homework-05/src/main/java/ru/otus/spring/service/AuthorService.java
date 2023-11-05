package ru.otus.spring.service;

import ru.otus.spring.dto.author.AuthorModel;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorModel> findAll();

    Optional<AuthorModel> findById(long id);

    AuthorModel save(AuthorModel authorModel);
}

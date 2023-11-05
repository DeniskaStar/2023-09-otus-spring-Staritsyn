package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.AuthorMapper;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.repository.AuthorRepository;
import ru.otus.spring.dto.author.AuthorModel;
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
    public List<AuthorModel> findAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toModel)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorModel> findById(long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toModel);
    }

    @Override
    @Transactional
    public AuthorModel save(AuthorModel authorModel) {
        var savedAuthor = authorRepository.save(prepareAuthor(authorModel));
        return authorMapper.toModel(savedAuthor);
    }

    private Author prepareAuthor(AuthorModel authorModel) {
        return authorMapper.toDao(authorModel);
    }
}

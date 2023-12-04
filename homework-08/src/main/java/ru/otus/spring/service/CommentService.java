package ru.otus.spring.service;

import ru.otus.spring.dto.comment.CommentCreateEditDto;
import ru.otus.spring.dto.comment.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentDto> findAllByBookId(String bookId);

    Optional<CommentDto> findById(String id);

    CommentDto create(CommentCreateEditDto comment);

    CommentDto update(String id, CommentCreateEditDto comment);

    void deleteById(String id);
}

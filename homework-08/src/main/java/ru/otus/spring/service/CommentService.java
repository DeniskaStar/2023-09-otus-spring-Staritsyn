package ru.otus.spring.service;

import ru.otus.spring.dto.comment.CommentCreateDto;
import ru.otus.spring.dto.comment.CommentDto;
import ru.otus.spring.dto.comment.CommentUpdateDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentDto> findAllByBookId(String bookId);

    Optional<CommentDto> findById(String id);

    CommentDto create(CommentCreateDto comment);

    CommentDto update(CommentUpdateDto comment);

    void deleteById(String id);
}

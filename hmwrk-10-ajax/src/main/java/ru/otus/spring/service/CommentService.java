package ru.otus.spring.service;

import ru.otus.spring.dto.comment.CommentCreateDto;
import ru.otus.spring.dto.comment.CommentDto;
import ru.otus.spring.dto.comment.CommentUpdateDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAllByBookId(long bookId);

    CommentDto findById(long id);

    CommentDto create(CommentCreateDto commentCreateDto);

    CommentDto update(CommentUpdateDto commentUpdateDto);

    void deleteById(long id);
}

package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.CommentMapper;
import ru.otus.spring.data.domain.Comment;
import ru.otus.spring.data.repository.BookRepository;
import ru.otus.spring.data.repository.CommentRepository;
import ru.otus.spring.dto.comment.CommentCreateDto;
import ru.otus.spring.dto.comment.CommentDto;
import ru.otus.spring.dto.comment.CommentUpdateDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllByBookId(long bookId) {
        return commentRepository.findAllByBookId(bookId).stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto);
    }

    @Override
    @Transactional
    public CommentDto create(CommentCreateDto comment) {
        var savedComment = commentRepository.save(prepareCommentToCreate(comment));
        return commentMapper.toDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDto update(CommentUpdateDto comment) {
        validateExistComment(comment);
        var updatedComment = commentRepository.save(prepareCommentToUpdate(comment));
        return commentMapper.toDto(updatedComment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Comment prepareCommentToCreate(CommentCreateDto comment) {
        var book = bookRepository.findById(comment.getBookId())
                .orElseThrow(() -> new NotFoundException("Book [id: %d] not found".formatted(comment.getBookId())));
        return commentMapper.toEntity(comment, book);
    }

    private Comment prepareCommentToUpdate(CommentUpdateDto updateComment) {
        var comment = prepareCommentToCreate(updateComment);
        comment.setId(updateComment.getId());
        return comment;
    }

    private void validateExistComment(CommentUpdateDto comment) {
        var existComment = findById(comment.getId());
        if (existComment.isEmpty()) {
            throw new NotFoundException("Comment [id: %d] not found".formatted(comment.getId()));
        }
    }
}

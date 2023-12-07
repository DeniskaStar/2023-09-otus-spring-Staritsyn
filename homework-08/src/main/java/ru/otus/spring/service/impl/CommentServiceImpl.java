package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.CommentMapper;
import ru.otus.spring.data.domain.Comment;
import ru.otus.spring.data.repository.CommentRepository;
import ru.otus.spring.data.repository.book.BookRepository;
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
    public List<CommentDto> findAllByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId).stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(String id) {
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
        var existComment = commentRepository.findById(comment.getId())
                .orElseThrow(() -> new NotFoundException("Comment [id: %s] not found"));
        validateBook(existComment, comment);
        existComment.setText(comment.getText());
        return commentMapper.toDto(existComment);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    private void validateBook(Comment existComment, CommentCreateDto comment) {
        if (!existComment.getBook().getId().equals(comment.getBookId())) {
            throw new IllegalArgumentException("BookId [id: %s] not equal to book from exist comment"
                    .formatted(comment.getBookId()));
        }
    }

    private Comment prepareCommentToCreate(CommentCreateDto comment) {
        var book = bookRepository.findById(comment.getBookId())
                .orElseThrow(() -> new NotFoundException("Book [id: %s] not found".formatted(comment.getBookId())));
        return commentMapper.toEntity(comment, book);
    }
}

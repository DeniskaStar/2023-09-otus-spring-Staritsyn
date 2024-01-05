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
    public CommentDto findById(long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Comment [id: %d] not found".formatted(id)));
    }

    @Override
    @Transactional
    public CommentDto create(CommentCreateDto commentCreateDto) {
        var savedComment = commentRepository.save(prepareCommentToCreate(commentCreateDto));
        return commentMapper.toDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDto update(CommentUpdateDto commentUpdateDto) {
        var existingComment = commentRepository.findById(commentUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("Comment [id: %d] not found"));
        validateBook(existingComment, commentUpdateDto);
        commentMapper.copy(commentUpdateDto, existingComment);
        return commentMapper.toDto(existingComment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private void validateBook(Comment existComment, CommentUpdateDto comment) {
        if (!existComment.getBook().getId().equals(comment.getBookId())) {
            throw new IllegalArgumentException("BookId [id: %d] not equal to book from exist comment"
                    .formatted(comment.getBookId()));
        }
    }

    private Comment prepareCommentToCreate(CommentCreateDto comment) {
        var book = bookRepository.findById(comment.getBookId())
                .orElseThrow(() -> new NotFoundException("Book [id: %d] not found".formatted(comment.getBookId())));
        return commentMapper.toEntity(comment, book);
    }
}

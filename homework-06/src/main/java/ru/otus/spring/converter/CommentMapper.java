package ru.otus.spring.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Comment;
import ru.otus.spring.dto.comment.CommentCreateDto;
import ru.otus.spring.dto.comment.CommentDto;

@RequiredArgsConstructor
@Component
public class CommentMapper {

    private final BookMapper bookMapper;

    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        if (comment.getBook() != null) {
            commentDto.setBook(bookMapper.toDto(comment.getBook()));
        }
        return commentDto;
    }

    public Comment toEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        if (commentDto.getBook() != null) {
            comment.setBook(bookMapper.toEntity(commentDto.getBook()));
        }
        return comment;
    }

    public Comment toEntity(CommentCreateDto commentCreateDto, Book book) {
        Comment comment = new Comment();
        comment.setText(commentCreateDto.getText());
        comment.setBook(book);
        return comment;
    }

    public String commentToString(CommentDto comment) {
        return "Id: %d, Text: %s, Book: {%s}".formatted(
                comment.getId(),
                comment.getText(),
                bookMapper.bookToString(comment.getBook()));
    }
}

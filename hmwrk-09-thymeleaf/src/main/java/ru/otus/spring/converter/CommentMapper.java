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

    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    public Comment toEntity(CommentCreateDto commentCreateDto, Book book) {
        Comment comment = new Comment();
        comment.setText(commentCreateDto.getText());
        comment.setBook(book);
        return comment;
    }
}

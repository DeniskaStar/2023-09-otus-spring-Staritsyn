package ru.otus.spring.dto.comment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CommentUpdateDto extends CommentCreateDto {

    private String id;

    public CommentUpdateDto(String id, String text, String bookId) {
        super(text, bookId);
        this.id = id;
    }
}

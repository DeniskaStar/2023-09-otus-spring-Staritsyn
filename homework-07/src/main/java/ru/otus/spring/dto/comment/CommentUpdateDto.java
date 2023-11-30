package ru.otus.spring.dto.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommentUpdateDto extends CommentCreateDto {

    private Long id;

    public CommentUpdateDto(Long id, String text, Long bookId) {
        super(text, bookId);
        this.id = id;
    }
}

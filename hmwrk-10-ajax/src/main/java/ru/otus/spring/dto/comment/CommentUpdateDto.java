package ru.otus.spring.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentUpdateDto {
    @NotNull
    private Long id;

    @NotBlank
    private String text;

    @NotNull
    private Long bookId;
}

package ru.otus.spring.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthorUpdateDto {
    @NotNull
    private Long id;

    @NotBlank
    private String fullName;
}

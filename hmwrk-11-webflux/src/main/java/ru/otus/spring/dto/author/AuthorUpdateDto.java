package ru.otus.spring.dto.author;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorUpdateDto {
    @NotBlank
    private String fullName;
}

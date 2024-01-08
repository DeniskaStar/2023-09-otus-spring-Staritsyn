package ru.otus.spring.dto.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreUpdateDto {
    @NotBlank
    private String name;
}

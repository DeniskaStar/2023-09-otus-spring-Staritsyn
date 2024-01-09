package ru.otus.spring.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDto {
    @NotBlank
    private String title;

    @NotNull
    private Long authorId;

    @NotEmpty
    private Set<Long> genreIds;
}

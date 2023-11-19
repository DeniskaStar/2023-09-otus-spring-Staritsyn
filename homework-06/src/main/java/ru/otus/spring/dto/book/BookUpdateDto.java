package ru.otus.spring.dto.book;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookUpdateDto extends BookCreateDto {

    private Long id;

    public BookUpdateDto(Long id, String title, Long authorId, Set<Long> genreIds) {
        super(title, authorId, genreIds);
        this.id = id;
    }
}

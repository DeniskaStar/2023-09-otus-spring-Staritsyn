package ru.otus.spring.dto.book;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookUpdateDto extends BookCreateDto {

    private Long id;

    public BookUpdateDto(Long id, String title, Long authorId, List<Long> genreIds) {
        super(title, authorId, genreIds);
        this.id = id;
    }
}

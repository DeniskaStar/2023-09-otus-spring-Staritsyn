package ru.otus.spring.dto.book;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BookUpdateDto extends BookCreateDto {

    private String id;

    public BookUpdateDto(String id, String title, String authorId, Set<String> genreIds) {
        super(title, authorId, genreIds);
        this.id = id;
    }
}

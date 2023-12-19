package ru.otus.spring.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {

    private String title;

    private Long authorId;

    private Set<Long> genreIds = new HashSet<>();
}

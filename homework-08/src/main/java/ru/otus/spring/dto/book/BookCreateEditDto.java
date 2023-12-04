package ru.otus.spring.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateEditDto {

    private String title;

    private String authorId;

    private Set<String> genreIds = new HashSet<>();
}

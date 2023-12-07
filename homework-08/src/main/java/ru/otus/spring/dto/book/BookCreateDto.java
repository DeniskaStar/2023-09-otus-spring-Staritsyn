package ru.otus.spring.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {

    private String title;

    private String authorId;

    private Set<String> genreIds = new LinkedHashSet<>();
}

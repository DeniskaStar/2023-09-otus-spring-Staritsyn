package ru.otus.spring.dto.book;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.genre.GenreDto;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String title;

    private AuthorDto author;

    private Set<GenreDto> genres = new LinkedHashSet<>();
}

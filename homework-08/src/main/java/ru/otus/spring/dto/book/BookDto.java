package ru.otus.spring.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.genre.GenreDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String title;

    private AuthorDto author;

    private List<GenreDto> genres = new ArrayList<>();
}

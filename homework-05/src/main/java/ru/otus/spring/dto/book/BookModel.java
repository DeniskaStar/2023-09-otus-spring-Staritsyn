package ru.otus.spring.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.dto.author.AuthorModel;
import ru.otus.spring.dto.genre.GenreModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookModel {

    private Long id;

    private String title;

    private AuthorModel author;

    private List<GenreModel> genres;
}

package ru.otus.spring.data.repository.extractor.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.data.domain.Genre;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookGenreRelation {

    private Long bookId;

    private List<Genre> genres;
}

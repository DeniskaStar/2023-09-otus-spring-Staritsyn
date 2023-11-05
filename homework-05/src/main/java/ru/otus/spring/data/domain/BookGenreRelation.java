package ru.otus.spring.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookGenreRelation {

    private Long bookId;

    private Long genreId;
}

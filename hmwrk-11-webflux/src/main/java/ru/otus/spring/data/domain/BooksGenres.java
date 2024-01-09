package ru.otus.spring.data.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books_genres")
public class BooksGenres {

    @Id
    private Long id;

    @Column("book_id")
    private Long bookId;

    @Column("genre_id")
    private Long genreId;

    public BooksGenres(Long bookId, Long genreId) {
        this.bookId = bookId;
        this.genreId = genreId;
    }
}

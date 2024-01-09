package ru.otus.spring.data.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    private Long id;

    @Column("title")
    private String title;

    @Column("author_id")
    private Long authorId;

    @Transient
    private Author author;

    @Transient
    private List<Genre> genres;

    public Book(Long id) {
        this.id = id;
    }
}

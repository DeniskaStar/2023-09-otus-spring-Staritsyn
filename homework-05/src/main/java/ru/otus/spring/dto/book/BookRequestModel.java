package ru.otus.spring.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestModel {

    private Long id;

    private String title;

    private Long authorId;

    private List<Long> genreIds;

    public static BookRequestModel of(String title, Long authorId, List<Long> genreIds) {
        return new BookRequestModel(null, title, authorId, genreIds);
    }

    public static BookRequestModel of(Long id, String title, Long authorId, List<Long> genreIds) {
        var bookRequest = of(title, authorId, genreIds);
        bookRequest.setId(id);
        return bookRequest;
    }
}

package ru.otus.spring.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {

    private String title;

    private Long authorId;

    private List<Long> genreIds = new ArrayList<>();
}

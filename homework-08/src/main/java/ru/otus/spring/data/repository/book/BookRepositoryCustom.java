package ru.otus.spring.data.repository.book;

import java.util.List;

public interface BookRepositoryCustom {

    void removeGenresArrayElementsById(String id);

    List<String> findAllBookIdByAuthorId(String authorId);
}

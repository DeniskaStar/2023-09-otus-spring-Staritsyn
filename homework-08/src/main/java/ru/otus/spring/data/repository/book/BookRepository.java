package ru.otus.spring.data.repository.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.data.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    List<Book> findByAuthorId(String authorId);

    void removeByAuthorId(String authorId);
}

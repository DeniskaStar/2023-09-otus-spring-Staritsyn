package ru.otus.spring.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.data.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBookId(String bookId);

    void removeByBookId(String bookId);
}

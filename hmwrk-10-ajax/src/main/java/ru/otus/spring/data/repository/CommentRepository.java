package ru.otus.spring.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.data.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBookId(long bookId);
}

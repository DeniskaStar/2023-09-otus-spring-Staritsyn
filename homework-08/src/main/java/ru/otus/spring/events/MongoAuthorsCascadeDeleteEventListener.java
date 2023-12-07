package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.repository.CommentRepository;
import ru.otus.spring.data.repository.book.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MongoAuthorsCascadeDeleteEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event) {
        super.onAfterDelete(event);
        String authorId = event.getSource().get("_id").toString();
        List<Book> bookIds = bookRepository.findByAuthorId(authorId);
        deleteCommentsByBook(bookIds);

        bookRepository.removeByAuthorId(authorId);
    }

    private void deleteCommentsByBook(List<Book> bookIds) {
        bookIds.stream()
                .map(Book::getId)
                .forEach(commentRepository::removeByBookId);
    }
}

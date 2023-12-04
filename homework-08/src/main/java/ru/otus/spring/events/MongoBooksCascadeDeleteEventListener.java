package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.repository.CommentRepository;
import ru.otus.spring.data.repository.book.BookRepository;

@RequiredArgsConstructor
@Component
public class MongoBooksCascadeDeleteEventListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);

        // При удалении книги напрямую по id (не через каскадное удаление автора)
        Object bookId = event.getSource().get("_id");
        if (bookId != null) {
            bookId = event.getSource().get("_id").toString();
            commentRepository.removeByBookId((String) bookId);
        }
    }
}

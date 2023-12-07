package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.data.repository.book.BookRepository;

@RequiredArgsConstructor
@Component
public class MongoGenresCascadeDeleteEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Genre> event) {
        super.onAfterDelete(event);
        String genreId = event.getSource().get("_id").toString();
        bookRepository.removeGenresArrayElementsById(genreId);
    }
}

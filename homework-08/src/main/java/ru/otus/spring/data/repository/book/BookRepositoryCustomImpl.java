package ru.otus.spring.data.repository.book;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.data.domain.Book;

import java.util.List;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeGenresArrayElementsById(String id) {
        val query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        val update = new Update().pull("genres", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public List<String> findAllBookIdByAuthorId(String authorId) {
        Query query = Query.query(Criteria.where("author.$id").is(new ObjectId(authorId)));
        List<Book> books = mongoTemplate.find(query, Book.class);
        return books.stream()
                .map(Book::getId)
                .toList();
    }
}

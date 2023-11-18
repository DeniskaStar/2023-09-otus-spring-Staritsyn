package ru.otus.spring.data.repository.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.repository.BookRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@RequiredArgsConstructor
@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-entity-graph");
        return Optional.ofNullable(em.find(Book.class, id, Map.of(FETCH.getKey(), entityGraph)));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}

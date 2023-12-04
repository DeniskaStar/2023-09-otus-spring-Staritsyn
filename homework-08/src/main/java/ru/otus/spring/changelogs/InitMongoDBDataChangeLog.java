package ru.otus.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Comment;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.data.repository.AuthorRepository;
import ru.otus.spring.data.repository.CommentRepository;
import ru.otus.spring.data.repository.GenreRepository;
import ru.otus.spring.data.repository.book.BookRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private final List<Author> authorList = new ArrayList<>();

    private final List<Genre> genreList = new ArrayList<>();

    private final List<Book> bookList = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "staritsyndy", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthor", author = "staritsyndy", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        Author author1 = authorRepository.save(new Author("Author #1"));
        Author author2 = authorRepository.save(new Author("Author #2"));
        authorList.add(0, author1);
        authorList.add(1, author2);
    }

    @ChangeSet(order = "002", id = "initGenres", author = "staritsyndy", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        Genre genre1 = genreRepository.save(new Genre("Genre #1"));
        Genre genre2 = genreRepository.save(new Genre("Genre #2"));
        Genre genre3 = genreRepository.save(new Genre("Genre #3"));
        Genre genre4 = genreRepository.save(new Genre("Genre #4"));
        genreList.add(0, genre1);
        genreList.add(1, genre2);
        genreList.add(2, genre3);
        genreList.add(3, genre4);
    }

    @ChangeSet(order = "003", id = "initBooks", author = "staritsyndy", runAlways = true)
    public void initBooks(BookRepository bookRepository) {
        Book book1 = bookRepository.save(new Book("Book #1", authorList.get(0),
                List.of(genreList.get(0), genreList.get(1))));
        Book book2 = bookRepository.save(new Book("Book #2", authorList.get(1),
                List.of(genreList.get(2), genreList.get(3))));
        bookList.add(0, book1);
        bookList.add(0, book2);
    }

    @ChangeSet(order = "004", id = "initComments", author = "staritsyndy", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
        commentRepository.save(new Comment("Comment #1", bookList.get(0)));
        commentRepository.save(new Comment("Comment #2", bookList.get(0)));
        commentRepository.save(new Comment("Comment #3", bookList.get(1)));
        commentRepository.save(new Comment("Comment #4", bookList.get(1)));
    }
}

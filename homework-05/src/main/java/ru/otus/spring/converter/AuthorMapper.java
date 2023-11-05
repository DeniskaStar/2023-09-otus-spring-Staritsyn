package ru.otus.spring.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.dto.author.AuthorModel;

@RequiredArgsConstructor
@Component
public class AuthorMapper {

    public AuthorModel toModel(Author author) {
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(author.getId());
        authorModel.setFullName(author.getFullName());
        return authorModel;
    }

    public Author toDao(AuthorModel authorModel) {
        Author author = new Author();
        author.setId(authorModel.getId());
        author.setFullName(authorModel.getFullName());
        return author;
    }

    public String authorToString(AuthorModel author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}

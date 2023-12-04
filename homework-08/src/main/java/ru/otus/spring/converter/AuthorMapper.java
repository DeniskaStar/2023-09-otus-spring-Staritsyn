package ru.otus.spring.converter;

import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.dto.author.AuthorCreateEditDto;
import ru.otus.spring.dto.author.AuthorDto;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setFullName(author.getFullName());
        return authorDto;
    }

    public Author toEntity(AuthorCreateEditDto authorDto) {
        Author author = new Author();
        copy(authorDto, author);
        return author;
    }

    public Author toEntity(AuthorCreateEditDto fromAuthor, Author toAuthor) {
        copy(fromAuthor, toAuthor);
        return toAuthor;
    }

    private void copy(AuthorCreateEditDto source, Author target) {
        target.setFullName(source.getFullName());
    }

    public String authorToString(AuthorDto author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}

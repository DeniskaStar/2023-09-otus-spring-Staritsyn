package ru.otus.spring.converter;

import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.dto.author.AuthorCreateDto;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.author.AuthorUpdateDto;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setFullName(author.getFullName());
        return authorDto;
    }

    public Author toEntity(AuthorCreateDto authorCreateDto) {
        Author author = new Author();
        author.setFullName(authorCreateDto.getFullName());
        return author;
    }

    public void copy(AuthorUpdateDto authorUpdateDto, Author existingAuthor) {
        existingAuthor.setFullName(authorUpdateDto.getFullName());
    }
}

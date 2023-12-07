package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.converter.AuthorMapper;
import ru.otus.spring.dto.author.AuthorCreateDto;
import ru.otus.spring.dto.author.AuthorUpdateDto;
import ru.otus.spring.service.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorMapper authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find author by id", key = "abid")
    public String findAuthorById(String id) {
        return authorService.findById(id)
                .map(authorConverter::authorToString)
                .orElse("Author with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Insert author", key = "ains")
    public String insertAuthor(String fullName) {
        var authorCreateRequest = new AuthorCreateDto(fullName);
        var savedAuthor = authorService.create(authorCreateRequest);
        return authorConverter.authorToString(savedAuthor);
    }

    @ShellMethod(value = "Update author", key = "aupd")
    public String updateAuthor(String id, String fullName) {
        var authorUpdateRequest = new AuthorUpdateDto(id, fullName);
        var savedAuthor = authorService.update(authorUpdateRequest);
        return authorConverter.authorToString(savedAuthor);
    }

    @ShellMethod(value = "Delete author by id", key = "adel")
    public void deleteAuthor(String id) {
        authorService.deleteById(id);
    }
}

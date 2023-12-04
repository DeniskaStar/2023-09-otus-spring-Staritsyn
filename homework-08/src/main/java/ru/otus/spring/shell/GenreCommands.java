package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.converter.GenreMapper;
import ru.otus.spring.dto.genre.GenreCreateEditDto;
import ru.otus.spring.service.GenreService;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    private final GenreMapper genreConverter;

    @ShellMethod(value = "Find all genres", key = "ag")
    public String findAllGenres() {
        return genreService.findAll().stream()
                .map(genreConverter::genreToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find genres by Ids", key = "gbid")
    public String findGenresByIds(Set<String> genreIds) {
        return genreService.findByIds(genreIds).stream()
                .map(genreConverter::genreToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Insert genre", key = "gins")
    public String insertGenre(String name) {
        var genreCreateRequest = new GenreCreateEditDto(name);
        var savedGenre = genreService.create(genreCreateRequest);
        return genreConverter.genreToString(savedGenre);
    }

    @ShellMethod(value = "Update genre", key = "gupd")
    public String updateGenre(String id, String name) {
        var genreUpdateRequest = new GenreCreateEditDto(name);
        var savedGenre = genreService.update(id, genreUpdateRequest);
        return genreConverter.genreToString(savedGenre);
    }

    @ShellMethod(value = "Delete genre by id", key = "gdel")
    public void deleteGenre(String id) {
        genreService.deleteById(id);
    }
}

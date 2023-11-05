package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.converter.GenreMapper;
import ru.otus.spring.dto.genre.GenreModel;
import ru.otus.spring.service.GenreService;

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

    @ShellMethod(value = "Insert genre", key = "gins")
    public String insertGenre(String name) {
        var genreRequest = prepareGenre(null, name);
        var savedGenre = genreService.save(genreRequest);
        return genreConverter.genreToString(savedGenre);
    }

    @ShellMethod(value = "Update author", key = "gupd")
    public String updateGenre(long id, String name) {
        var genreRequest = prepareGenre(id, name);
        var savedGenre = genreService.save(genreRequest);
        return genreConverter.genreToString(savedGenre);
    }

    private GenreModel prepareGenre(Long id, String name) {
        return GenreModel.builder()
                .id(id)
                .name(name)
                .build();
    }
}

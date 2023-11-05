package ru.otus.spring.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.dto.genre.GenreModel;

@RequiredArgsConstructor
@Component
public class GenreMapper {

    public GenreModel toModel(Genre genre) {
        GenreModel genreModel = new GenreModel();
        genreModel.setId(genre.getId());
        genreModel.setName(genre.getName());
        return genreModel;
    }

    public Genre toDao(GenreModel genreModel) {
        Genre genre = new Genre();
        genre.setId(genreModel.getId());
        genre.setName(genreModel.getName());
        return genre;
    }

    public String genreToString(GenreModel genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }
}

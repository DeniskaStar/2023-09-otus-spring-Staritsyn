package ru.otus.spring.converter;

import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.dto.genre.GenreDto;

@Component
public class GenreMapper {

    public GenreDto toDto(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        return genreDto;
    }

    public Genre toEntity(GenreDto genreDto) {
        Genre genre = new Genre();
        genre.setId(genreDto.getId());
        genre.setName(genreDto.getName());
        return genre;
    }

    public String genreToString(GenreDto genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }
}

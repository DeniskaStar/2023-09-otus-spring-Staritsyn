package ru.otus.spring.converter;

import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.dto.genre.GenreCreateDto;
import ru.otus.spring.dto.genre.GenreDto;

@Component
public class GenreMapper {

    public GenreDto toDto(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        return genreDto;
    }

    public Genre toEntity(GenreCreateDto genreDto) {
        Genre genre = new Genre();
        copy(genreDto, genre);
        return genre;
    }

    public Genre toEntity(GenreCreateDto fromGenre, Genre toGenre) {
        copy(fromGenre, toGenre);
        return toGenre;
    }

    private void copy(GenreCreateDto source, Genre target) {
        target.setName(source.getName());
    }

    public String genreToString(GenreDto genre) {
        return "Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }
}

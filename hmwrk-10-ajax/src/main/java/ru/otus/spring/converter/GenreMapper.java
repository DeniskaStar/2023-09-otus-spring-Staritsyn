package ru.otus.spring.converter;

import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.dto.genre.GenreCreateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.dto.genre.GenreUpdateDto;

@Component
public class GenreMapper {

    public GenreDto toDto(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        return genreDto;
    }

    public Genre toEntity(GenreCreateDto genreCreateDtoDto) {
        Genre genre = new Genre();
        genre.setName(genreCreateDtoDto.getName());
        return genre;
    }

    public void copy(GenreUpdateDto genreUpdateDto, Genre existingGenre) {
        if (genreUpdateDto.getName() != null) {
            existingGenre.setName(genreUpdateDto.getName());
        }
    }
}

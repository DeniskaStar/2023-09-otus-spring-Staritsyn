package ru.otus.spring.converter;

import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.BooksGenres;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.dto.genre.GenreCreateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.dto.genre.GenreUpdateDto;

import java.util.Collection;
import java.util.stream.Collectors;

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
        existingGenre.setName(genreUpdateDto.getName());

    }

    public Collection<BooksGenres> toBookGenre(Long bookId, Collection<Long> genreIds) {
        return genreIds.stream()
                .map(genreId -> new BooksGenres(bookId, genreId))
                .collect(Collectors.toSet());
    }

    public Collection<Long> extractGenreIdsFromBooksGenres(Collection<BooksGenres> booksGenres) {
        return booksGenres.stream().map(BooksGenres::getGenreId).collect(Collectors.toSet());
    }
}

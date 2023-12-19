package ru.otus.spring.dto.genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private Long id;

    private String name;

    public static GenreDto of(GenreCreateDto genre) {
        return GenreDto.builder()
                .name(genre.getName())
                .build();
    }

    public static GenreDto of(GenreUpdateDto genre) {
        var genreDto = of((GenreCreateDto) genre);
        genreDto.setId(genre.getId());
        return genreDto;
    }
}

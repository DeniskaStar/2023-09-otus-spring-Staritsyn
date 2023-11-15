package ru.otus.spring.dto.genre;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenreUpdateDto extends GenreCreateDto {

    private Long id;

    public GenreUpdateDto(Long id, String name) {
        super(name);
        this.id = id;
    }
}

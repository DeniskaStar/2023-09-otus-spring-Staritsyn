package ru.otus.spring.dto.genre;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class GenreUpdateDto extends GenreCreateDto {

    private String id;

    public GenreUpdateDto(String id, String name) {
        super(name);
        this.id = id;
    }
}

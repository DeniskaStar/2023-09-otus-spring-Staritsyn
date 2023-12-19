package ru.otus.spring.dto.author;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorUpdateDto extends AuthorCreateDto {

    private Long id;

    public AuthorUpdateDto(Long id, String fullName) {
        super(fullName);
        this.id = id;
    }
}

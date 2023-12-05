package ru.otus.spring.dto.author;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AuthorUpdateDto extends AuthorCreateDto {

    private String id;

    public AuthorUpdateDto(String id, String fullName) {
        super(fullName);
        this.id = id;
    }
}

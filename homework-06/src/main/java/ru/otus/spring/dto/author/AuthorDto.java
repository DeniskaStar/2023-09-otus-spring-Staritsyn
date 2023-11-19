package ru.otus.spring.dto.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private Long id;

    private String fullName;

    public static AuthorDto of(AuthorCreateDto author) {
        return AuthorDto.builder()
                .fullName(author.getFullName())
                .build();
    }

    public static AuthorDto of(AuthorUpdateDto author) {
        var authorDto = of((AuthorCreateDto) author);
        authorDto.setId(author.getId());
        return authorDto;
    }
}

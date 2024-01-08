package ru.otus.spring.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.converter.AuthorMapper;
import ru.otus.spring.data.repository.AuthorRepository;
import ru.otus.spring.dto.author.AuthorCreateDto;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.author.AuthorUpdateDto;
import ru.otus.spring.exception.NotFoundException;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @GetMapping(value = "api/v1/authors", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AuthorDto> findAll() {
        return authorRepository.findAll()
                .map(authorMapper::toDto);
    }

    @GetMapping("api/v1/authors/{id}")
    public Mono<ResponseEntity<AuthorDto>> findById(@PathVariable("id") Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new NotFoundException("Author [id: %d] not found".formatted(id))));
    }

    @PostMapping("/api/v1/authors")
    public Mono<ResponseEntity<AuthorDto>> create(@RequestBody @Valid Mono<AuthorCreateDto> authorCreateDto) {
        return authorCreateDto
                .map(authorMapper::toEntity)
                .flatMap(authorRepository::save)
                .map(authorMapper::toDto)
                .map(it -> new ResponseEntity<>(it, HttpStatus.CREATED));
    }

    @PutMapping("/api/v1/authors/{id}")
    public Mono<ResponseEntity<AuthorDto>> update(@PathVariable("id") Long id,
                                                  @RequestBody @Valid Mono<AuthorUpdateDto> authorUpdateDto) {
        return authorRepository.findById(id)
                .flatMap(existingAuthor -> authorUpdateDto
                        .doOnNext(it -> authorMapper.copy(it, existingAuthor))
                        .flatMap(it -> authorRepository.save(existingAuthor))
                        .map(authorMapper::toDto)
                        .map(updatedAuthorDto -> new ResponseEntity<>(updatedAuthorDto, HttpStatus.OK))
                )
                .switchIfEmpty(Mono.error(new NotFoundException("Author [id: %d] not found".formatted(id))));
    }

    @DeleteMapping("/api/v1/authors/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Long id) {
        return authorRepository.deleteById(id)
                .map(empty -> ResponseEntity.noContent().build());
    }
}

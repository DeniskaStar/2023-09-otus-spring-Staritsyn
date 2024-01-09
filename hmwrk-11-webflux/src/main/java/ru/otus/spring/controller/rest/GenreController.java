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
import ru.otus.spring.converter.GenreMapper;
import ru.otus.spring.data.repository.GenreRepository;
import ru.otus.spring.dto.genre.GenreCreateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.dto.genre.GenreUpdateDto;
import ru.otus.spring.exception.NotFoundException;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @GetMapping(value = "api/v1/genres", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GenreDto> findAll() {
        return genreRepository.findAll()
                .map(genreMapper::toDto);
    }

    @GetMapping("api/v1/genres/{id}")
    public Mono<ResponseEntity<GenreDto>> findById(@PathVariable("id") Long id) {
        return genreRepository.findById(id)
                .map(genreMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new NotFoundException("Genre [id: %d] not found".formatted(id))));
    }

    @PostMapping("/api/v1/genres")
    public Mono<ResponseEntity<GenreDto>> create(@RequestBody @Valid Mono<GenreCreateDto> genreCreateDto) {
        return genreCreateDto
                .map(genreMapper::toEntity)
                .flatMap(genreRepository::save)
                .map(genreMapper::toDto)
                .map(it -> new ResponseEntity<>(it, HttpStatus.CREATED));
    }

    @PutMapping("/api/v1/genres/{id}")
    public Mono<ResponseEntity<GenreDto>> update(@PathVariable("id") Long id,
                                                 @RequestBody @Valid Mono<GenreUpdateDto> genreUpdateDto) {
        return genreRepository.findById(id)
                .flatMap(existingGenre -> genreUpdateDto
                        .doOnNext(it -> genreMapper.copy(it, existingGenre))
                        .flatMap(it -> genreRepository.save(existingGenre))
                        .map(genreMapper::toDto)
                        .map(updatedGenreDto -> new ResponseEntity<>(updatedGenreDto, HttpStatus.OK))
                )
                .switchIfEmpty(Mono.error(new NotFoundException("Genre [id: %d] not found".formatted(id))));
    }

    @DeleteMapping("/api/v1/genres/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Long id) {
        return genreRepository.deleteById(id)
                .map(empty -> ResponseEntity.noContent().build());
    }
}

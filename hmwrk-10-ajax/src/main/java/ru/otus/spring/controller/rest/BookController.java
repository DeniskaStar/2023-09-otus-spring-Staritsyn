package ru.otus.spring.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;
import ru.otus.spring.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController extends ControllerBase {

    private final BookService bookService;

    @GetMapping("api/v1/books")
    public ResponseEntity<List<BookDto>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("api/v1/books/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable("id") long bookId) {
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @PostMapping("api/v1/books")
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookCreateDto bookCreateDto,
                                          BindingResult bindingResult) {
        throwValidationExceptionIfNeeded(bindingResult);
        return new ResponseEntity<>(bookService.create(bookCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("api/v1/books/{id}")
    public ResponseEntity<BookDto> update(@PathVariable("id") long id,
                                          @Valid @RequestBody BookUpdateDto bookUpdateDto,
                                          BindingResult bindingResult) {
        validatePathParams(id, bookUpdateDto.getId());
        throwValidationExceptionIfNeeded(bindingResult);
        return ResponseEntity.ok(bookService.update(bookUpdateDto));
    }

    @DeleteMapping("api/v1/books/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

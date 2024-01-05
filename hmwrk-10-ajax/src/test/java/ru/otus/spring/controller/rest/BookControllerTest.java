package ru.otus.spring.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.BookService;

import java.util.ArrayList;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BookController.class})
public class BookControllerTest {

    private static final long BOOK_FIRST_ID = 1;

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll_returnAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findById_shouldReturnBook_whenItExists() throws Exception {
        BookDto actualBook = BookDto.builder()
                .id(1L)
                .title("test_book")
                .build();

        when(bookService.findById(BOOK_FIRST_ID)).thenReturn(actualBook);

        mockMvc.perform(get("/api/v1/books/%d".formatted(BOOK_FIRST_ID)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(BOOK_FIRST_ID))
                .andExpect(jsonPath("$.title").value("test_book"));
    }

    @Test
    void findById_shouldReturnNotFound_whenBookNotExists() throws Exception {
        when(bookService.findById(BOOK_FIRST_ID)).thenThrow(new NotFoundException("Dummy book"));

        mockMvc.perform(get("/api/v1/books/%d".formatted(BOOK_FIRST_ID)))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldCreateBook_whenRequestIsValid() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setTitle("test_book");
        bookCreateDto.setAuthorId(1L);
        bookCreateDto.setGenreIds(Set.of(1L));

        BookDto actualBook = BookDto.builder()
                .id(1L)
                .title("test_book")
                .author(AuthorDto.builder().id(1L).build())
                .genres(Set.of(GenreDto.builder().id(1L).build()))
                .build();

        when(bookService.create(bookCreateDto)).thenReturn(actualBook);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("test_book"))
                .andExpect(jsonPath("$.author.id").value(1L))
                .andExpect(jsonPath("$.genres[0].id").value(1L));
    }

    @Test
    void create_shouldReturnBadRequest_whenRequestIsNotValid() throws Exception {
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BookCreateDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldUpdateBook_whenBookIsValid() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(BOOK_FIRST_ID);
        bookUpdateDto.setTitle("book_test");
        bookUpdateDto.setAuthorId(1L);
        bookUpdateDto.setGenreIds(Set.of(1L));

        BookDto actualBook = BookDto.builder()
                .id(1L)
                .title("book_test")
                .author(AuthorDto.builder().id(1L).build())
                .genres(Set.of(GenreDto.builder().id(1L).build()))
                .build();

        when(bookService.update(bookUpdateDto)).thenReturn(actualBook);

        mockMvc.perform(put("/api/v1/books/%d".formatted(BOOK_FIRST_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("book_test"))
                .andExpect(jsonPath("$.author.id").value(1L))
                .andExpect(jsonPath("$.genres[0].id").value(1L));
    }

    @Test
    void update_shouldReturnBadRequest_whenRequestIsNotValid() throws Exception {
        mockMvc.perform(put("/api/v1/books/%d".formatted(BOOK_FIRST_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BookUpdateDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_shouldReturnSuccessful() throws Exception {
        mockMvc.perform(delete("/api/v1/books/%d".formatted(BOOK_FIRST_ID)))
                .andExpect(status().isNoContent());
    }
}

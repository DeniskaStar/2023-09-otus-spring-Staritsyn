package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testIndexWhenGetRequestToRootThenRedirectToBooks() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    public void testFindAllWhenGetRequestToBooksThenReturnBooksView() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("book/books"));
    }

    @Test
    public void testFindByIdWhenGetRequestToBookWithExistingIdThenReturnBookView() throws Exception {
        Mockito.when(bookService.findById(1)).thenReturn(Optional.of(new BookDto()));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book/book"));
    }

    @Test
    public void testFindByIdWhenGetRequestToBookWithNonExistingIdThenReturnNotFound() throws Exception {
        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindCommentsByIdWhenGetRequestToBookWithExistingIdThenReturnCommentsView() throws Exception {
        Mockito.when(bookService.findById(1)).thenReturn(Optional.of(new BookDto()));

        mockMvc.perform(get("/books/1/comments"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("comments"))
                .andExpect(view().name("comment/comments"));
    }

    @Test
    public void testFindCommentsByIdWhenGetRequestToBookWithNonExistingIdThenReturnNotFound() throws Exception {
        mockMvc.perform(get("/books/999/comments"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testNewBookWhenGetRequestThenReturnCreateBookView() throws Exception {
        mockMvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book/create"));
    }

    @Test
    public void testCreateWhenPostRequestThenRedirectToBooks() throws Exception {
        mockMvc.perform(post("/books")
                        .param("title", "New Book Title")
                        .param("authorId", "1")
                        .param("genreIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    public void testUpdateWhenPostRequestThenRedirectToBook() throws Exception {
        mockMvc.perform(post("/books/1/update")
                        .param("id", "1")
                        .param("title", "Updated Book Title")
                        .param("authorId", "1")
                        .param("genreIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/1"));
    }

    @Test
    public void testDeleteWhenPostRequestThenRedirectToBooks() throws Exception {
        mockMvc.perform(post("/books/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }
}

package ru.otus.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;
import ru.otus.spring.dto.comment.CommentCreateDto;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    @GetMapping("/")
    public String index() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String findAll(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book/list";
    }

    @GetMapping("/books/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        BookDto book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book/edit";
    }

    @GetMapping("/books/{id}/comments")
    public String findCommentsById(@PathVariable("id") long id, Model model) {
        BookDto book = bookService.findById(id);
        model.addAttribute("comments", commentService.findAllByBookId(id));
        model.addAttribute("newComment", new CommentCreateDto());
        model.addAttribute("book", book);
        return "comment/comments";
    }

    @GetMapping("/books/new")
    public String newBook(Model model) {
        model.addAttribute("book", new BookCreateDto());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book/create";
    }

    @PostMapping("/books")
    public String create(@Valid @ModelAttribute("book") BookCreateDto bookCreateDto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "book/create";
        }
        bookService.create(bookCreateDto);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}/update")
    public String update(@PathVariable("id") long id, @Valid @ModelAttribute("book") BookUpdateDto bookUpdateDto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "book/edit_with_error";
        }
        model.addAttribute("book", bookService.update(bookUpdateDto));
        return "redirect:/books/" + id;
    }

    @PostMapping("/books/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }
}

package ru.otus.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.dto.comment.CommentCreateDto;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @PostMapping("/comments")
    public String create(@ModelAttribute("newComment") @Valid CommentCreateDto commentCreateDto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", bookService.findById(commentCreateDto.getBookId()));
            model.addAttribute("comments", commentService.findAllByBookId(commentCreateDto.getBookId()));
            return "comment/comments";
        }
        commentService.create(commentCreateDto);
        return "redirect:/books/%d/comments".formatted(commentCreateDto.getBookId());
    }
}

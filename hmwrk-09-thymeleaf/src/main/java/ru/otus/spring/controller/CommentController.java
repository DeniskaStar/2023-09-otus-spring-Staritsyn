package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.dto.comment.CommentCreateDto;
import ru.otus.spring.service.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public String create(@ModelAttribute CommentCreateDto commentCreateDto) {
        commentService.create(commentCreateDto);
        return "redirect:/books/" + commentCreateDto.getBookId() + "/comments";
    }
}

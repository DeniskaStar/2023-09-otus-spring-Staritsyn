package ru.otus.spring.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring.dto.comment.CommentDto;
import ru.otus.spring.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/books/{id}/comments")
    public ResponseEntity<List<CommentDto>> findCommentsByBookId(@PathVariable("id") long id) {
        return ResponseEntity.ok(commentService.findAllByBookId(id));
    }
}

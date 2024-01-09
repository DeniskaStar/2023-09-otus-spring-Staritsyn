package ru.otus.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalErrorController {

    @ExceptionHandler(value = {NotFoundException.class})
    public Mono<ResponseEntity<String>> notFoundException(NotFoundException e) {
        return Mono.just(new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public Mono<ResponseEntity<String>> badRequestException(BadRequestException e) {
        return Mono.just(new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<String>> handleException(WebExchangeBindException e) {
        var errors = e.getBindingResult();
        return Mono.just(ResponseEntity.badRequest().body(errors.toString()));
    }
}

package ru.otus.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorController {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<String> notFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<String> badRequestException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<String> badRequestException(ValidationException e) {
        return new ResponseEntity<>(e.getErrors().toString(), HttpStatus.BAD_REQUEST);
    }
}

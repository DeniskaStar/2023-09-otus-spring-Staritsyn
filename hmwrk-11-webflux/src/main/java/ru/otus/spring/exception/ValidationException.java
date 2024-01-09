package ru.otus.spring.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

public class ValidationException extends RuntimeException {

    @Getter
    private final Errors errors;

    public ValidationException(Errors errors) {
        super(errors.toString(), null, true, false);
        this.errors = errors;
    }
}

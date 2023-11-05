package ru.otus.spring.exception;

public class EntityByIdNotFoundException extends RuntimeException {

    public EntityByIdNotFoundException(String message) {
        super(message);
    }
}

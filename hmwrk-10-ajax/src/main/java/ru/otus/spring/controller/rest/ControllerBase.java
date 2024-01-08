package ru.otus.spring.controller.rest;

import org.springframework.validation.BindingResult;
import ru.otus.spring.exception.BadRequestException;
import ru.otus.spring.exception.ValidationException;

import java.util.Objects;

public class ControllerBase {

    protected void throwValidationExceptionIfNeeded(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    protected void validatePathParams(Long pathId, Long dtoAttributeId) {
        if (!Objects.equals(pathId, dtoAttributeId)) {
            throw new BadRequestException("Идентификатор пути [%s] не соответствует идентификатору модели [%s]"
                    .formatted(pathId, dtoAttributeId));
        }
    }
}

package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Answer {

    /**
     * Идентификатор ответа
     */
    private int id;

    /**
     * Текст ответа
     */
    private String text;

    /**
     * Является ли ответ правильным
     */
    private boolean correct;
}

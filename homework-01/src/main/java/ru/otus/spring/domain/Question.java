package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Question {

    /**
     * Идентификатор вопроса
     */
    private int id;

    /**
     * Текст вопроса
     */
    private String text;

    /**
     * Список ответов
     */
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();
}

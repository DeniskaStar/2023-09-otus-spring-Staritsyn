package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface QuestionDao {

    /**
     * Получение списка вопросов
     *
     * @return список вопросов
     */
    List<Question> getAll();
}

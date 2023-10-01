package ru.otus.spring.service;

import ru.otus.spring.domain.Examination;

public interface ExaminationService {

    /**
     * Получение модели тестирования из cvs-файла
     *
     * @return модель тестирования
     */
    Examination getExamination();

    /**
     * Вывод списка вопросов в консоль
     *
     */
    void printExamination(Examination examination);
}

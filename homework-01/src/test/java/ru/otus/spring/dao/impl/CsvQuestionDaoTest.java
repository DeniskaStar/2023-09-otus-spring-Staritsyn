package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.FileNotFoundException;
import ru.otus.spring.exception.InvalidOperationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CsvQuestionDaoTest {

    private static final String csvFileName = "examination/questions.csv";

    private static final String badCsvFileName = "examination/bad_questions.csv";

    private static final String notExistFileName = "dummy.csv";

    @Test
    void getAll_shouldReturnQuestionsFromCsvFile_whenFileIsValid() {
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(csvFileName);

        List<Question> actualQuestions = csvQuestionDao.getAll();

        assertNotNull(actualQuestions);
        assertThat(actualQuestions).hasSize(5);
    }

    @Test
    void getAll_shouldThrowException_whenFileNotFound() {
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(notExistFileName);

        assertThrows(FileNotFoundException.class, csvQuestionDao::getAll);
    }

    @Test
    void getAll_shouldThrowException_whenFileIsNotValid() {
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(badCsvFileName);

        assertThrows(InvalidOperationException.class, csvQuestionDao::getAll);
    }
}

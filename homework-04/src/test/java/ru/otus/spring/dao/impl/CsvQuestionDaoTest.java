package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.config.impl.AppConfig;
import ru.otus.spring.exception.FileNotFoundException;
import ru.otus.spring.exception.QuestionReadException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class CsvQuestionDaoTest {

    private static final String VALID_FILE_NAME = "examination/questions_test.csv";
    private static final String NOT_EXISTS_FILE_NAME = "examination/dummy.csv";
    private static final String NOT_VALID_FILE_NAME = "examination/bad_questions_test.csv";

    @MockBean
    private AppConfig config;

    @Autowired
    private CsvQuestionDao questionDao;

    @Test
    void getAll_shouldReturnQuestionsFromCsvFile_whenFileIsValid() {
        when(config.getExaminationFileName()).thenReturn(VALID_FILE_NAME);

        var actualQuestions = questionDao.getAll();

        assertThat(actualQuestions).hasSize(5);
        verify(config).getExaminationFileName();
    }

    @Test
    void getAll_shouldThrowException_whenFileNotFound() {
        when(config.getExaminationFileName()).thenReturn(NOT_EXISTS_FILE_NAME);

        assertThrows(FileNotFoundException.class, questionDao::getAll);
    }

    @Test
    void getAll_shouldThrowException_whenFileIsNotValid() {
        when(config.getExaminationFileName()).thenReturn(NOT_VALID_FILE_NAME);

        assertThrows(QuestionReadException.class, questionDao::getAll);
    }
}

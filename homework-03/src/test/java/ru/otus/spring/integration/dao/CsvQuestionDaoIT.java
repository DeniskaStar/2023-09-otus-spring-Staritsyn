package ru.otus.spring.integration.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.dao.QuestionDao;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class CsvQuestionDaoIT {

    @Autowired
    private QuestionDao questionDao;

    @Test
    void getAll_shouldReturnQuestionsFromCsvFile_whenFileIsValid() {
        var actualQuestions = questionDao.getAll();

        assertThat(actualQuestions).hasSize(5);
    }
}

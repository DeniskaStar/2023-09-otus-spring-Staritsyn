package ru.otus.spring.integration.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.Main;
import ru.otus.spring.dao.QuestionDao;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Main.class})
public class CsvQuestionDaoIT {

    @Autowired
    private QuestionDao questionDao;

    @Test
    void getAll_shouldReturnQuestionsFromCsvFile_whenFileIsValid() {
        var actualQuestions = questionDao.getAll();

        assertThat(actualQuestions).hasSize(5);
    }
}

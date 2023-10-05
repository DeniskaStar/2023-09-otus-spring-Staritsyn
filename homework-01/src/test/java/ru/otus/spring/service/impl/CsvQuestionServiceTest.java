package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.IOService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CsvQuestionServiceTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @InjectMocks
    private CsvQuestionService csvQuestionService;

    @Test
    public void getAndPrintAll_shouldPrintQuestions_whenQuestionsExists() {
        List<Answer> expectedAnswers = Arrays.asList(new Answer(1, "Answer 1", true), new Answer(2, "Answer 2", false));
        List<Question> expectedQuestions = Collections.singletonList(new Question(1, "Question 1", expectedAnswers));
        when(questionDao.getAll()).thenReturn(expectedQuestions);

        csvQuestionService.getAndPrintAll();

        verify(ioService, times(1)).println("\nQuestion №1. Question 1");
        verify(ioService, times(1)).println("1) Answer 1;");
        verify(ioService, times(1)).println("2) Answer 2;");
    }

    @Test
    public void getAndPrintAll_shouldThrowException_whenQuestionsIsEmpty() {
        when(questionDao.getAll()).thenReturn(Collections.emptyList());

        Assertions.assertThrows(IllegalArgumentException.class, () -> csvQuestionService.getAndPrintAll());
    }
}

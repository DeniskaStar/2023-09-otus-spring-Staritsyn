package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.config.ExaminationRightCountAnswerProvider;
import ru.otus.spring.domain.ExaminationResult;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.IOService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ResultServiceImplTest {

    private static final String SUCCESS_RESULT_TEXT = "Congratulations! You passed test!";
    private static final String FAIL_RESULT_TEXT = "Sorry. You fail test.";

    @Mock
    private ExaminationRightCountAnswerProvider appConfig;

    @Mock
    private IOService ioService;

    @InjectMocks
    private ResultServiceImpl resultService;

    @Test
    void showResult_shouldOutputSuccessResultTest_whenRequiredNumberAnswersCollected() {
        var examination = ExaminationResult.builder()
                .student(new Student("dummy", "dummy"))
                .answeredQuestions(List.of(Question.builder().build()))
                .rightAnswersCount(3)
                .build();
        when(appConfig.getRightAnswersCountToPass()).thenReturn(2);

        resultService.showResult(examination);

        verify(ioService).printLine(SUCCESS_RESULT_TEXT);
    }

    @Test
    void showResult_shouldOutputFailResultTest_whenRequiredNumberAnswersNotCollected() {
        var examination = ExaminationResult.builder()
                .student(new Student("dummy", "dummy"))
                .answeredQuestions(List.of(Question.builder().build()))
                .rightAnswersCount(2)
                .build();
        when(appConfig.getRightAnswersCountToPass()).thenReturn(4);

        resultService.showResult(examination);

        verify(ioService).printLine(FAIL_RESULT_TEXT);
    }
}

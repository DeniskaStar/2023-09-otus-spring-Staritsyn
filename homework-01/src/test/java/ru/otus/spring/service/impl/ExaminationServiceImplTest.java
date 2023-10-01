package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Examination;
import ru.otus.spring.service.CsvFileService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminationServiceImplTest {

    @Mock
    private CsvFileService csvFileService;

    @InjectMocks
    private ExaminationServiceImpl examinationService;

    @Test
    public void getExamination_shouldReturnExamination_whenCsvFileIsValid() {
        List<List<String>> csvValues = Arrays.asList(
                Arrays.asList("question_id", "question_text", "answer_id", "answer_text", "answer_correct"),
                Arrays.asList("1", "Question 1", "1", "Answer 1", "1"),
                Arrays.asList("2", "Question 2", "1", "Answer 1", "0")
        );
        when(csvFileService.readFile(any(), anyChar())).thenReturn(csvValues);

        Examination examination = examinationService.getExamination();

        assertNotNull(examination);
        assertEquals(2, examination.getQuestions().size());
        assertTrue(examination.getQuestions().get(0).getAnswers().get(0).isCorrect());

    }

    @Test
    public void getExamination_shouldThrowException_whenFileHasInvalidFormat() {
        List<List<String>> csvValues = Arrays.asList(
                Arrays.asList("question_id", "question_text", "answer_id", "answer_text", "answer_correct"),
                Arrays.asList("1", "Question 1", "1", "Answer 1")
        );
        when(csvFileService.readFile(any(), anyChar())).thenReturn(csvValues);

        assertThrows(IllegalArgumentException.class, () -> examinationService.getExamination());
    }
}

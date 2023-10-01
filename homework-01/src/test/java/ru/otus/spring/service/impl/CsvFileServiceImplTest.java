package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.CsvFileReader;
import ru.otus.spring.util.Chars;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvFileServiceImplTest {

    @Mock
    private CsvFileReader csvFileReader;

    @InjectMocks
    private CsvFileServiceImpl csvFileService;

    @Test
    public void readFile_shouldReadFile_whenFileIsValid() {
        List<List<String>> expectedCsvValues = Arrays.asList(Arrays.asList("test1", "test2"), Arrays.asList("test3", "test4"));
        when(csvFileReader.readFile(anyString(), anyChar())).thenReturn(expectedCsvValues);

        List<List<String>> actualCsvValues = csvFileService.readFile("dummy.csv", Chars.COMMA);

        assertThat(actualCsvValues).isEqualTo(expectedCsvValues);
        verify(csvFileReader).readFile("dummy.csv", Chars.COMMA);
    }

    @Test
    public void readFile_shouldThrowException_whenFileIsEmpty() {
        when(csvFileReader.readFile(anyString(), anyChar())).thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> csvFileService.readFile("dummy.csv", Chars.COMMA));
    }

    @Test
    public void readFile_shouldThrowException_whenFileNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> csvFileService.readFile(null, Chars.COMMA));
    }
}

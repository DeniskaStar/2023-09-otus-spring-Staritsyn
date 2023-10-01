package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.util.Chars;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvFileReaderImplTest {

    private static final String csvFileName = "examination/questions.csv";

    private CsvFileReaderImpl csvFileReader;

    @BeforeEach
    public void initEach() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test
    public void readFile_shouldReturnCsvValues_whenFileIsValid() {
        List<List<String>> csvData = csvFileReader.readFile(csvFileName, Chars.COMMA);

        assertNotNull(csvData);
        assertEquals(16, csvData.size());
        assertEquals("What is the capital of France?", csvData.get(1).get(1));
    }

    @Test
    public void readFile_shouldThrowsException_whenFileIsNotExists() {
        assertThrows(IllegalArgumentException.class, () -> csvFileReader.readFile("dummy.csv", Chars.COMMA));
    }
}

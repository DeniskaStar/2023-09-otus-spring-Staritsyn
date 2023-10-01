package ru.otus.spring.dao.impl;

import ru.otus.spring.dao.CsvFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFileReaderImpl implements CsvFileReader {

    @Override
    public List<List<String>> readFile(String fileName, char separator) {
        InputStream is = getFileFromResourceAsStream(fileName);

        return getCsvValues(is, separator);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException(String.format("File [%s] not found ", fileName));
        } else {
            return inputStream;
        }
    }

    private List<List<String>> getCsvValues(InputStream is, char separator) {
        List<List<String>> resultCsvValues = new ArrayList<>();

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(String.valueOf(separator));
                resultCsvValues.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultCsvValues;
    }
}

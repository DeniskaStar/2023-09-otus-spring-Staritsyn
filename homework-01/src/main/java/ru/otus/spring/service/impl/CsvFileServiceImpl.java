package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import ru.otus.spring.dao.CsvFileReader;
import ru.otus.spring.service.CsvFileService;
import ru.otus.spring.util.AssertTextUtils;

import java.util.List;

@RequiredArgsConstructor
public class CsvFileServiceImpl implements CsvFileService {

    private final CsvFileReader csvFileReader;

    @Override
    public List<List<String>> readFile(String fileName, char separator) {
        Assert.notNull(fileName, AssertTextUtils.notNull(fileName));

        List<List<String>> csvValues = csvFileReader.readFile(fileName, separator);

        if (csvValues.isEmpty()) {
            throw new IllegalArgumentException(String.format("File [%s] is empty ", fileName));
        }

        return csvValues;
    }
}

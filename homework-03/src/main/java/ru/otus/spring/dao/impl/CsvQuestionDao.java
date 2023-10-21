package ru.otus.spring.dao.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.config.ExaminationFileNameProvider;
import ru.otus.spring.convert.mapper.QuestionDtoToQuestionMapper;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.dto.QuestionDto;
import ru.otus.spring.exception.FileNotFoundException;
import ru.otus.spring.exception.QuestionReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CsvQuestionDao implements QuestionDao {

    private final ExaminationFileNameProvider config;

    private final QuestionDtoToQuestionMapper questionDtoMapper;

    @Override
    public List<Question> getAll() {
        String examinationFileName = getExaminationFileName();
        List<Question> questions = getCsvValues(examinationFileName);

        if (questions.isEmpty()) {
            throw new QuestionReadException(String.format("File [%s] is empty", examinationFileName));
        }

        return questions;
    }

    private String getExaminationFileName() {
        return config.getExaminationFileName();
    }

    private List<Question> getCsvValues(String fileName) {
        try (InputStreamReader streamReader = new InputStreamReader(getFileAsStream(fileName), StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            return processFile(reader);
        } catch (IOException e) {
            throw new QuestionReadException(String.format("File [%s] reading error", e.getMessage()));
        }
    }

    private InputStream getFileAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new FileNotFoundException(String.format("File [%s] not found ", fileName));
        } else {
            return inputStream;
        }
    }

    private List<Question> processFile(BufferedReader reader) {
        List<QuestionDto> questions = new CsvToBeanBuilder<QuestionDto>(reader)
                .withType(QuestionDto.class)
                .withSeparator(';')
                .withSkipLines(1)
                .build()
                .parse();

        return convertToDomain(questions);
    }

    private List<Question> convertToDomain(List<QuestionDto> questions) {
        return questions.stream()
                .map(questionDtoMapper::map)
                .toList();
    }
}

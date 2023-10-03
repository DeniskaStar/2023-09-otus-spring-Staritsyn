package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.FileNotFoundException;
import ru.otus.spring.exception.InvalidOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private static final String COMMA_SEPARATOR = ",";

    private final String fileName;

    @Override
    public List<Question> getAll() {
        InputStream is = getFileFromResourceAsStream(fileName);
        List<Question> questions = getCsvValues(is);

        if (questions.isEmpty()) {
            throw new InvalidOperationException(String.format("File [%s] is empty", fileName));
        }

        return questions;
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new FileNotFoundException(String.format("File [%s] not found ", fileName));
        } else {
            return inputStream;
        }
    }

    private List<Question> getCsvValues(InputStream is) {
        List<Question> questions = new ArrayList<>();

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            boolean firstLineSkipped = false;
            String line;

            while ((line = reader.readLine()) != null) {
                // Первую строку необходимо пропустить (т.к. яляется заголовком)
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }

                prepareQuestions(line, questions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }

    private void prepareQuestions(String line, List<Question> questions) {
        String[] row = line.split(COMMA_SEPARATOR);

        if (row.length != 5) {
            throw new InvalidOperationException("Invalid number of fields in CSV row");
        }

        try {
            // Тк вопрос дублируется для нескольких строчек(с разными ответами), то сначала ищем существующий
            Question question = questions.stream()
                    .filter(it -> it.getId() == Integer.parseInt(row[0]))
                    .findFirst()
                    .orElseGet(() -> {
                        Question newQuestion = createQuestion(row);
                        questions.add(newQuestion);

                        return newQuestion;
                    });

            question.getAnswers().add(createAnswer(row));
        } catch (Exception e) {
            throw new InvalidOperationException(String.format("File content not valid, [%s]", e.getMessage()));
        }
    }

    private Question createQuestion(String[] row) {
        return Question.builder()
                .id(Integer.parseInt(row[0]))
                .text(row[1])
                .answers(new ArrayList<>())
                .build();
    }

    private Answer createAnswer(String[] row) {
        return Answer.builder()
                .id(Integer.parseInt(row[2]))
                .text(row[3])
                .correct(Integer.parseInt(row[4]) == 1)
                .build();
    }
}

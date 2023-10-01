package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Examination;
import ru.otus.spring.domain.Question;
import ru.otus.spring.model.CsvData;
import ru.otus.spring.service.CsvFileService;
import ru.otus.spring.service.ExaminationService;
import ru.otus.spring.util.Chars;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {

    private final String examinationFileName;

    private final CsvFileService csvFileService;

    @Override
    public Examination getExamination() {
        List<List<String>> csvValues = csvFileService.readFile(examinationFileName, Chars.COMMA);

        return createExaminationFromCsv(csvValues);
    }

    @Override
    public void printExamination(Examination examination) {
        System.out.println("TESTING!!!");
        System.out.printf("Testing identifier: %s \n%n", examination.getExaminationExtId());
        System.out.println("==================");

        for (Question question : examination.getQuestions()) {
            System.out.printf("\nQuestion №%d. %s%n", question.getId(), question.getText());

            question.getAnswers().forEach(it -> System.out.printf("%d) %s;%n", it.getId(), it.getText()));
        }
    }

    private Examination createExaminationFromCsv(List<List<String>> csvValues) {
        Examination examination = createExamination();
        boolean firstLineSkipped = false;

        for (List<String> row : csvValues) {
            // Первую строку необходимо пропустить (т.к. яляется заголовком)
            if (!firstLineSkipped) {
                firstLineSkipped = true;
                continue;
            }

            if (row.size() != 5) {
                throw new IllegalArgumentException("Invalid number of fields in CSV row");
            }

            CsvData csvData = prepareCsvData(row);
            Question question = prepareQuestion(examination, csvData);
            Answer answer = prepareAnswer(csvData);

            question.getAnswers().add(answer);
        }

        return examination;
    }

    private Examination createExamination() {
        return Examination.builder()
                .examinationExtId(UUID.randomUUID())
                .questions(new ArrayList<>())
                .build();
    }

    private CsvData prepareCsvData(List<String> row) {
        CsvData csvData;

        try {
            csvData = CsvData.builder()
                    .questionId(Integer.parseInt(row.get(0)))
                    .questionText(row.get(1))
                    .answerId(Integer.parseInt(row.get(2)))
                    .answerText(row.get(3))
                    .correctAnswer(Integer.parseInt(row.get(4)) == 1)
                    .build();
        } catch (Exception ex) {
            throw new IllegalArgumentException("File content does not match the required format");
        }

        return csvData;
    }

    private Question prepareQuestion(Examination examination, CsvData csvData) {
        // Тк вопрос дублируется для нескольких строчек(с разными ответами), то сначала ищем существующий
        return examination.getQuestions().stream()
                .filter(it -> it.getId() == csvData.getQuestionId())
                .findFirst()
                .orElseGet(() -> {
                    Question newQuestion = Question.builder()
                            .id(csvData.getQuestionId())
                            .text(csvData.getQuestionText())
                            .answers(new ArrayList<>())
                            .build();
                    examination.getQuestions().add(newQuestion);
                    return newQuestion;
                });
    }

    private Answer prepareAnswer(CsvData csvData) {
        return Answer.builder()
                .id(csvData.getAnswerId())
                .text(csvData.getAnswerText())
                .correct(csvData.isCorrectAnswer())
                .build();
    }
}

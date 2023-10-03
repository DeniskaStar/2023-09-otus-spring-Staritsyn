package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.util.AssertTextUtils;

import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionService implements QuestionService {

    private final QuestionDao questionDao;

    private final IOService ioService;

    @Override
    public void getAndPrintAll() {
        List<Question> questions = questionDao.getAll();
        printQuestions(questions);
    }

    private void printQuestions(List<Question> questions) {
        Assert.notEmpty(questions, AssertTextUtils.collectionNotEmpty("questions"));

        for (Question question : questions) {
            ioService.println(String.format("\nQuestion №%d. %s", question.getId(), question.getText()));

            question.getAnswers().forEach(it -> ioService.println(String.format("%d) %s;", it.getId(), it.getText())));
        }
    }
}

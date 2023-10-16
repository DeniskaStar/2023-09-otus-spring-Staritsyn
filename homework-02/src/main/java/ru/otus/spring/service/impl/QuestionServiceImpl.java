package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.ExaminationResult;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.QuestionService;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final String PROMPT_MESSAGE = "Please, input number correct answer";

    private static final String ERROR_MESSAGE = "Invalid answer number, try again";

    private final QuestionDao questionDao;

    private final IOService ioService;

    @Override
    public ExaminationResult executeExamination(Student student) {
        showExaminationManual();
        var questions = questionDao.getAll();
        var examinationResult = createExaminationResult(student);

        for (Question question : questions) {
            showQuestionWithAnswers(question);
            processInputAnswer(examinationResult, question);
        }

        return examinationResult;
    }

    private void showExaminationManual() {
        ioService.printLine("");
        ioService.printFormattedLine("Please, answer the questions below");
    }

    private ExaminationResult createExaminationResult(Student student) {
        return ExaminationResult.of(student);
    }

    private void showQuestionWithAnswers(Question question) {
        int answerCount = 1;
        ioService.printLine("");
        ioService.printLine(question.getText());

        for (Answer answer : question.getAnswers()) {
            ioService.printFormattedLine("%d. %s", answerCount, answer.getText());
            answerCount++;
        }
    }

    private void processInputAnswer(ExaminationResult examinationResult, Question question) {
        var answer = getAnswerFromStudent(question);
        examinationResult.applyAnswer(question, answer.isCorrect());
    }

    private Answer getAnswerFromStudent(Question question) {
        var numberAnswer = ioService.readIntForRangeWithPrompt(1, 3, PROMPT_MESSAGE, ERROR_MESSAGE);

        // счетчик вопросов в тестировании начинается с 1
        return question.getAnswers().get(numberAnswer - 1);
    }
}

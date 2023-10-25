package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ExaminationRightCountAnswerProvider;
import ru.otus.spring.domain.ExaminationResult;
import ru.otus.spring.service.LocalizedIOService;
import ru.otus.spring.service.ResultService;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private static final String TEST_INFO_MESSAGE = "ResultService.test.results";

    private static final String STUDENT_INFO_MESSAGE = "ResultService.student";

    private static final String RESULT_TEST_PASSED_MESSAGE = "ResultService.passed.test";

    private static final String RESULT_TEST_FAIL_MESSAGE = "ResultService.fail.test";

    private static final String ANSWERED_QUESTIONS_MESSAGE = "ResultService.answered.questions.count";

    private static final String RIGHT_ANSWERS_MESSAGE = "ResultService.right.answers.count";

    private final ExaminationRightCountAnswerProvider appConfig;

    private final LocalizedIOService ioService;

    @Override
    public void showResult(ExaminationResult examinationResult) {
        showExaminationResultHeader(examinationResult);

        if (examinationResult.getRightAnswersCount() >= appConfig.getRightAnswersCountToPass()) {
            ioService.printLineLocalized(RESULT_TEST_PASSED_MESSAGE);
            return;
        }

        ioService.printLineLocalized(RESULT_TEST_FAIL_MESSAGE);
    }

    private void showExaminationResultHeader(ExaminationResult examinationResult) {
        int size = examinationResult.getAnsweredQuestions().size();

        ioService.printLine("");
        ioService.printLineLocalized(TEST_INFO_MESSAGE);
        ioService.printFormattedLineLocalized(STUDENT_INFO_MESSAGE, examinationResult.getStudent().getfullName());
        ioService.printFormattedLineLocalized(ANSWERED_QUESTIONS_MESSAGE, size);
        ioService.printFormattedLineLocalized(RIGHT_ANSWERS_MESSAGE, examinationResult.getRightAnswersCount());
    }
}

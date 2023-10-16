package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ExaminationRightCountAnswerProvider;
import ru.otus.spring.domain.ExaminationResult;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.ResultService;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final ExaminationRightCountAnswerProvider appConfig;

    private final IOService ioService;

    @Override
    public void showResult(ExaminationResult examinationResult) {
        showExaminationResultHeader(examinationResult);

        if (examinationResult.getRightAnswersCount() >= appConfig.getRightAnswersCountToPass()) {
            ioService.printLine("Congratulations! You passed test!");
            return;
        }

        ioService.printLine("Sorry. You fail test.");
    }

    private void showExaminationResultHeader(ExaminationResult examinationResult) {
        ioService.printLine("");
        ioService.printLine("Test results: ");
        ioService.printFormattedLine("Student: %s", examinationResult.getStudent().getfullName());
    }
}

package ru.otus.spring.config.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.ExaminationFileNameProvider;
import ru.otus.spring.config.ExaminationRightCountAnswerProvider;

@Component
public class AppConfig implements ExaminationFileNameProvider, ExaminationRightCountAnswerProvider {

    private final String examinationFileName;

    private final int rightAnswersCountToPass;

    public AppConfig(@Value("${examination.fileName}") String examinationFileName,
                     @Value("${examination.rightAnswersCountToPass}") int rightAnswersCountToPass) {
        this.examinationFileName = examinationFileName;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
    }

    @Override
    public String getExaminationFileName() {
        return examinationFileName;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }
}

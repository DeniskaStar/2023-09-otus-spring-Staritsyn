package ru.otus.spring.config.impl;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import ru.otus.spring.config.ExaminationFileNameProvider;
import ru.otus.spring.config.ExaminationRightCountAnswerProvider;
import ru.otus.spring.config.LocaleConfig;

import java.util.Locale;
import java.util.Map;

@Getter
@ConfigurationProperties(prefix = "examination")
public class AppConfig implements ExaminationRightCountAnswerProvider, LocaleConfig, ExaminationFileNameProvider {

    private final int rightAnswersCountToPass;

    private final Locale locale;

    private final Map<String, String> fileNameByLocaleTag;

    @ConstructorBinding
    public AppConfig(Map<String, String> fileNameByLocaleTag,
                     int rightAnswersCountToPass,
                     Locale locale) {
        this.fileNameByLocaleTag = fileNameByLocaleTag;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.locale = locale;
    }

    @Override
    public String getExaminationFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }
}

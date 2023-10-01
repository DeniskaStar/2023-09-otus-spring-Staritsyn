package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Examination {

    /**
     * Идентификатор тестирования
     */
    private UUID examinationExtId;

    /**
     * Список вопросов
     */
    @Builder.Default
    private List<Question> questions = new ArrayList<>();
}

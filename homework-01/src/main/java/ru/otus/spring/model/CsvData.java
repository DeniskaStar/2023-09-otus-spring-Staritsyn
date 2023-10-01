package ru.otus.spring.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CsvData {

    private int questionId;

    private String questionText;

    private int answerId;

    private String answerText;

    private boolean correctAnswer;
}

package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ExaminationResult {

    private Student student;

    @Builder.Default
    private List<Question> answeredQuestions = new ArrayList<>();

    private int rightAnswersCount;

    public static ExaminationResult of(Student student) {
        return ExaminationResult.builder()
                .student(student)
                .build();
    }

    public void applyAnswer(Question question, boolean isRightAnswer) {
        answeredQuestions.add(question);
        if (isRightAnswer) {
            rightAnswersCount++;
        }
    }
}

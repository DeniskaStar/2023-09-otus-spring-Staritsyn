package ru.otus.spring.service;

import ru.otus.spring.domain.ExaminationResult;
import ru.otus.spring.domain.Student;

public interface QuestionService {

    ExaminationResult executeExamination(Student student);
}

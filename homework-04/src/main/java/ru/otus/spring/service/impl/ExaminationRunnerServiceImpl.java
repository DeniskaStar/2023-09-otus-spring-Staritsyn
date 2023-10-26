package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.ExaminationRunnerService;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.ResultService;
import ru.otus.spring.service.StudentService;

@RequiredArgsConstructor
@Service
public class ExaminationRunnerServiceImpl implements ExaminationRunnerService {

    private final StudentService studentService;

    private final QuestionService questionService;

    private final ResultService resultService;

    @Override
    public void run() {
        var student = studentService.determinateCurrentStudent();
        var examinationResult = questionService.executeExamination(student);
        resultService.showResult(examinationResult);
    }
}

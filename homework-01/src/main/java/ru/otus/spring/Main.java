package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Examination;
import ru.otus.spring.service.ExaminationService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ExaminationService examinationService = context.getBean(ExaminationService.class);

        Examination examination = examinationService.getExamination();
        examinationService.printExamination(examination);
    }
}

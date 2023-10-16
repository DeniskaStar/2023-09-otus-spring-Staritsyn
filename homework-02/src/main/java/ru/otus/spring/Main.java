package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.ExaminationRunnerService;

@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Main.class);
        var testRunnerService = context.getBean(ExaminationRunnerService.class);

        testRunnerService.run();
    }
}

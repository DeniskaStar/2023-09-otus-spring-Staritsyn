package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.config.impl.AppConfig;
import ru.otus.spring.service.ExaminationRunnerService;

@EnableConfigurationProperties(value = {AppConfig.class})
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        var context = SpringApplication.run(Main.class);
        var testRunnerService = context.getBean(ExaminationRunnerService.class);

        testRunnerService.run();
    }
}

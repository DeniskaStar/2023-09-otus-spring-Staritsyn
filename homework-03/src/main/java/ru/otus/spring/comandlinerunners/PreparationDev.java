package ru.otus.spring.comandlinerunners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.otus.spring.service.ExaminationRunnerService;

@Profile("!test")
@RequiredArgsConstructor
@Component
public class PreparationDev implements CommandLineRunner {

    private final ExaminationRunnerService examinationRunnerService;

    @Override
    public void run(String... args) {
        examinationRunnerService.run();
    }
}

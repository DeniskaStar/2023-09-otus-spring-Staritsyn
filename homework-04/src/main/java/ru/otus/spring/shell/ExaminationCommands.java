package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.ExaminationRunnerService;
import ru.otus.spring.service.LocalizedIOService;

@RequiredArgsConstructor
@ShellComponent
public class ExaminationCommands {

    private final ExaminationRunnerService examinationRunnerService;

    private final LocalizedIOService ioService;

    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        ioService.printFormattedLine("Добро пожаловать: %s", userName);
    }

    @ShellMethod(value = "Run examination command", key = {"r", "run"})
    @ShellMethodAvailability(value = "isRunExaminationCommandAvailable")
    public void runExamination() {
        examinationRunnerService.run();
    }

    private Availability isRunExaminationCommandAvailable() {
        return userName == null ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}

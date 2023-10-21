package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.LocalizedIOService;
import ru.otus.spring.service.StudentService;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final LocalizedIOService ioService;

    @Override
    public Student determinateCurrentStudent() {
        String firstName = ioService.readStringWithPromptLocalized("StudentService.input.first.name");
        String lastName = ioService.readStringWithPromptLocalized("StudentService.input.last.name");

        return new Student(firstName, lastName);
    }
}

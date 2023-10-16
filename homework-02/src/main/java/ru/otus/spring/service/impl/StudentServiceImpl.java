package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.StudentService;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    @Override
    public Student determinateCurrentStudent() {
        String firstName = ioService.readStringWithPrompt("Please, write your firstName");
        String lastName = ioService.readStringWithPrompt("Please, write your lastName");

        return new Student(firstName, lastName);
    }
}

package ru.otus.spring.service.impl;

import ru.otus.spring.service.IOService;

public class ConsoleIOService implements IOService {

    @Override
    public void println(String line) {
        System.out.println(line);
    }
}

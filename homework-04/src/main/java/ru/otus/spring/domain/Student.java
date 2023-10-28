package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {

    private String firstName;

    private String lastName;

    public String getfullName() {
        return String.format("%s %s", firstName, lastName);
    }
}

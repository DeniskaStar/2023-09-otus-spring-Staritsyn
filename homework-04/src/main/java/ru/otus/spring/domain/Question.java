package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Question {

    private String text;

    @Builder.Default
    private List<Answer> answers = new ArrayList<>();
}

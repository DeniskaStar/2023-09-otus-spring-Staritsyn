package ru.otus.spring.convert.mapper;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import ru.otus.spring.dto.QuestionDto;

@Component
public class QuestionDtoToQuestionMapper {

    public Question map(QuestionDto value) {
        return Question.builder()
                .text(value.getText())
                .answers(value.getAnswers())
                .build();
    }
}

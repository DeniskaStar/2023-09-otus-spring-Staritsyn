package ru.otus.spring.convert.mapper;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.otus.spring.domain.Question;
import ru.otus.spring.dto.QuestionDto;
import ru.otus.spring.util.AssertTextUtils;

@Component
public class QuestionDtoToQuestionMapper {

    public Question map(QuestionDto value) {
        Assert.notNull(value, AssertTextUtils.notNull("questionDto"));

        return Question.builder()
                .text(value.getText())
                .answers(value.getAnswers())
                .build();
    }
}

package ru.otus.spring.convert.mapper;

import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.dto.QuestionDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuestionDtoToQuestionMapperTest {

    private final QuestionDtoToQuestionMapper questionMapper = new QuestionDtoToQuestionMapper();

    @Test
    void map_shouldReturnQuestion_whereQuestionDtoIsValid() {
        var questionDto = QuestionDto.builder()
                .text("What is the capital of France?")
                .answers(List.of(
                        new Answer("London", false),
                        new Answer("Paris", true),
                        new Answer("Berlin", false)
                )).build();

        var actualQuestion = questionMapper.map(questionDto);

        assertThat(actualQuestion).isNotNull();
        assertThat(actualQuestion.getText()).isEqualTo("What is the capital of France?");
    }

    @Test
    void map_shouldThrowException_whereQuestionDtoIsNull() {
        QuestionDto questionDto = null;

        assertThrows(IllegalArgumentException.class, () -> questionMapper.map(questionDto));
    }
}

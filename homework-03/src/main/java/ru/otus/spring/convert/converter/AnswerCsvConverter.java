package ru.otus.spring.convert.converter;

import com.opencsv.bean.AbstractCsvConverter;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Answer;

@Component
public class AnswerCsvConverter extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String value) {
        String[] values = value.split("%");
        return new Answer(values[0], Boolean.parseBoolean(values[1]));
    }
}

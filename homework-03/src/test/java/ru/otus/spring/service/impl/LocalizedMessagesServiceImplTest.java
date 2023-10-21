package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.spring.config.LocaleConfig;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocalizedMessagesServiceImplTest {

    @Mock
    private LocaleConfig localeConfig;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private LocalizedMessagesServiceImpl localizedMessagesService;

    @Test
    void getMessage_shouldReturnMessageWithLocale_whenLocaleIsCorrect() {
        String expectedMessage = "Денис";
        Locale ruLocale = new Locale("ru");
        when(localeConfig.getLocale()).thenReturn(ruLocale);
        when(messageSource.getMessage("Student.first.name", null, ruLocale)).thenReturn(expectedMessage);

        String actualMessage = localizedMessagesService.getMessage("Student.first.name", null);

        assertNotNull(actualMessage);
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}

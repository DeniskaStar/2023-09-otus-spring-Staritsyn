package ru.otus.spring.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.config.impl.AppConfig;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class LocalizedMessagesServiceImplTest {

    private static final String FIRST_NAME_MESSAGE_CODE = "Student.first.name";

    @MockBean
    private AppConfig localeConfig;

    @MockBean
    private MessageSource messageSource;

    @Autowired
    private LocalizedMessagesServiceImpl localizedMessagesService;

    @Test
    void getMessage_shouldReturnMessageWithLocale_whenLocaleIsCorrect() {
        String expectedMessage = "Денис";
        Locale ruLocale = new Locale("ru");
        when(localeConfig.getLocale()).thenReturn(ruLocale);
        when(messageSource.getMessage(FIRST_NAME_MESSAGE_CODE, null, ruLocale)).thenReturn(expectedMessage);

        String actualMessage = localizedMessagesService.getMessage(FIRST_NAME_MESSAGE_CODE, (Object[]) null);

        assertNotNull(actualMessage);
        assertThat(actualMessage).isEqualTo(expectedMessage);
        verify(messageSource).getMessage(FIRST_NAME_MESSAGE_CODE, null, ruLocale);
    }
}

package ru.otus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.Main;

@ActiveProfiles("test")
@SpringBootTest(classes = {Main.class})
class Homework04ApplicationTests {

    @Test
    void contextLoads() {
    }

}

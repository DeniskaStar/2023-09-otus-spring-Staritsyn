package ru.otus.spring.data.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест JPA-репозитория для работы с жанра")
@Sql({"classpath:sql/data.sql"})
@ActiveProfiles("test")
@DataJpaTest
class JpaCommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("должен вернуть список комментариев по id книги")
    @Test
    void findAll_shouldReturnAllCommentsByBookId() {
        var actualGenres = commentRepository.findAllByBookId(1L);

        assertThat(actualGenres).hasSize(3)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getText()))
                .allMatch(it -> it.getBook().getId().equals(1L));
    }
}

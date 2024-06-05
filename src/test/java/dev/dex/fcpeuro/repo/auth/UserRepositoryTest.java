package dev.dex.fcpeuro.repo.auth;

import dev.dex.fcpeuro.entity.auth.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void canSelectExistsEmail() {
        // given
        String email = "email";
        User user = new User(
                1, email, "username", "password", Role.USER, true, null
        );
        underTest.save(user);

        // when
        Boolean res = underTest.selectExistsEmail(email);

        // then
        assertThat(res).isTrue();
    }

    @Test
    void canSelectExistsUsername() {
        // given
        String username = "username";
        User user = new User(
                1, "email", username, "password", Role.USER, true, null
        );
        underTest.save(user);

        // when
        Boolean res = underTest.selectExistsUsername(username);

        // then
        assertThat(res).isTrue();
    }
}
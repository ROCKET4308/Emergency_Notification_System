package com.notificationservice.repository;

import com.notificationservice.entity.Role;
import com.notificationservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        User user = new User(1, "user@mail.com", "12345", Role.USER);
        User savedUser = userRepository.save(user);
        User returnUser = userRepository.findByEmail("user@mail.com").orElseThrow(() -> new IllegalArgumentException("User with this email is not exist"));
        assertEquals(savedUser, returnUser);
    }

}
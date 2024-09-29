package ru.ima.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.ima.model.jpa.User;

public class SignUpTest extends BaseTest {
    @Test
    public void signUpTest() {
        ResponseEntity<User> response = signUp("email", "fullName", "password");
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        User got = response.getBody();

        Assertions.assertEquals("email", got.getEmail());
        Assertions.assertEquals("fullName", got.getFullName());
        Assertions.assertNotNull(got.getId());
    }
}

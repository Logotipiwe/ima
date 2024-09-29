package ru.ima.tests;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.ima.model.dto.LoginResponse;
import ru.ima.model.jpa.User;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class LogInTest extends BaseTest {
    @Test
    public void logInTest() {
        signUp("email", "name", "1234");
        ResponseEntity<LoginResponse> response = logIn("email", "1234");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());
    }

    @Test
    public void notAuthenticatedTest() {
        signUp("mail", "name", "1234");
        ResponseEntity<LoginResponse> login = logIn("mail", "1234");
        ResponseEntity<List<User>> response2 = getUsers(Objects.requireNonNull(login.getBody()).getToken());
        assertEquals(200, response2.getStatusCode().value());
    }


}

package ru.ima.tests;

import org.flywaydb.core.Flyway;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ima.model.dto.LoginResponse;
import ru.ima.model.dto.LoginUserDto;
import ru.ima.model.dto.RegisterUserDto;
import ru.ima.model.jpa.Project;
import ru.ima.model.jpa.User;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@FlywayTest(locationsForMigrate = {"db/migration"})
public class BaseTest {
    @Autowired
    private Flyway flyway;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    @BeforeEach
    public void before() {
        flyway.clean();
        flyway.migrate();
    }

    protected ResponseEntity<LoginResponse> logIn(String email, String password) {
        LoginUserDto user = new LoginUserDto(email, password);

        return restTemplate.exchange("http://localhost:" + this.port + "/ima/auth/login",
                HttpMethod.POST,
                new HttpEntity<>(user),
                LoginResponse.class
        );
    }

    protected ResponseEntity<List<User>> getUsers(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "jwt=" + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("http://localhost:"+this.port+"/ima/api/users",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<User>>() {}
        );
    }

    protected ResponseEntity<User> signUp(String email, String fullName, String password) {
        RegisterUserDto user = new RegisterUserDto(email, password, fullName);

        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + this.port + "/ima/auth/signup",
                HttpMethod.POST,
                new HttpEntity<>(user),
                User.class
        );
        return response;
    }

    protected ResponseEntity<Project> createProject(String token, String name) {
        Project project = new Project();
        project.setName(name);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "jwt=" + token);
        HttpEntity<Project> entity = new HttpEntity<>(project, headers);

        return restTemplate.exchange("http://localhost:" + this.port + "/ima/api/projects",
                HttpMethod.POST,
                entity,
                Project.class
        );
    }

    protected ResponseEntity<List<Project>> getProjects(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "jwt=" + token);
        HttpEntity<Project> entity = new HttpEntity<>(headers);

        return restTemplate.exchange("http://localhost:" + this.port + "/ima/api/projects",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
    }
}

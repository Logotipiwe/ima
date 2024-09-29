package ru.ima.tests;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.ima.model.dto.LoginResponse;
import ru.ima.model.jpa.Project;
import ru.ima.model.jpa.User;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectsTest extends BaseTest {
    @Test
    public void createProjectTest() {
        signUp("email", "name", "1234");
        ResponseEntity<LoginResponse> response = logIn("email", "1234");
        String token = Objects.requireNonNull(response.getBody()).getToken();

        ResponseEntity<Project> project = this.createProject(token, "project1");
        assertTrue(project.getStatusCode().is2xxSuccessful());
        assertNotNull(project.getBody());
        assertNotNull(project.getBody().getId());
        assertEquals("project1", project.getBody().getName());
    }

    @Test
    public void manyProjectsSavedTest() {
        signUp("email", "name", "1234");
        ResponseEntity<LoginResponse> response = logIn("email", "1234");
        String token = Objects.requireNonNull(response.getBody()).getToken();

        this.createProject(token, "project1");
        this.createProject(token, "project2");

        ResponseEntity<List<Project>> projects = this.getProjects(token);

        assertTrue(projects.getStatusCode().is2xxSuccessful());
        assertNotNull(projects.getBody());
        assertEquals(2, projects.getBody().size());
    }
}

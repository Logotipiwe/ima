package ru.ima.controller;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ima.model.jpa.Project;
import ru.ima.model.jpa.User;
import ru.ima.model.jpa.UserProject;
import ru.ima.repo.ProjectRepo;
import ru.ima.repo.UserProjectRepo;
import ru.ima.service.GitlabIntegrationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ima/api/git")
public class GitIntegrationController {
    private final GitlabIntegrationService gitlabIntegrationService;

    public GitIntegrationController(GitlabIntegrationService gitlabIntegrationService) {
        this.gitlabIntegrationService = gitlabIntegrationService;
    }

    @RequestMapping("/gitlab")
    public ResponseEntity integrateProjects(
            @AuthenticationPrincipal User user,
            @RequestParam("token") String token
    ) throws IOException {
        return ResponseEntity.ok(gitlabIntegrationService.integrateProjectsAndIssues(user, token));
    }
}

package ru.ima.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ima.model.jpa.User;
import ru.ima.service.GitHubIntegrationService;
import ru.ima.service.GitlabIntegrationService;

import java.io.IOException;

@RestController
@RequestMapping("/ima/api/git")
public class GitIntegrationController {
    private final GitlabIntegrationService gitlabIntegrationService;
    private final GitHubIntegrationService gitHubIntegrationService;

    public GitIntegrationController(GitlabIntegrationService gitlabIntegrationService, GitHubIntegrationService gitHubIntegrationService) {
        this.gitlabIntegrationService = gitlabIntegrationService;
        this.gitHubIntegrationService = gitHubIntegrationService;
    }

    @RequestMapping("/gitlab")
    public ResponseEntity integrateProjects(
            @AuthenticationPrincipal User user
    ) throws IOException {
        if(user.getGitlabToken() == null) {
            throw new RuntimeException("gitlab token is null");
        }
        return ResponseEntity.ok(gitlabIntegrationService.integrateProjectsAndIssues(user));
    }

    @RequestMapping("/github")
    public ResponseEntity integrateGHProjects(
            @AuthenticationPrincipal User user
    ) throws IOException {
        if(user.getGithubToken() == null) {
            throw new RuntimeException("github token is null");
        }
        return ResponseEntity.ok(/*gitHubIntegrationService.integrateProjects(user)*/).build();
    }
}

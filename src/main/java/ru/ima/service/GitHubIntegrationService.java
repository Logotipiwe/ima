package ru.ima.service;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Service;
import ru.ima.model.jpa.Project;
import ru.ima.model.jpa.User;
import ru.ima.repo.ProjectRepo;

import java.io.IOException;

@Service
public class GitHubIntegrationService {
    private final ProjectRepo projectRepo;

    public GitHubIntegrationService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public void createProject(User user, Project project) throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(user.getGithubToken()).build();
        GHRepository repository = github.createRepository(project.getName()).autoInit(true).create();
        project.setGithubLink(repository.getHtmlUrl().toString());
        project.setGithubId(repository.getId());
    }

    /*public Object integrateProjects(User user) throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(user.getGithubToken()).build();
        GHUser logotipiwe = github.getUser("Logotipiwe");
        List<GHProject> ghProjects = logotipiwe.listProjects().toList();
        List<Project> toSave = new ArrayList<>();
        for (GHProject ghProject : ghProjects) {
            Project project = new Project();
            project.setName(ghProject.getName());
            project.setGithubId(ghProject.getId());
            project.setGithubLink(ghProject.getHtmlUrl().toString());
            toSave.add(project);
        }
        return projectRepo.saveAll(toSave);
    }*/
}

package ru.ima.service;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabIssue;
import org.gitlab.api.models.GitlabProject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ima.model.enums.TaskStatus;
import ru.ima.model.jpa.Project;
import ru.ima.model.jpa.Task;
import ru.ima.model.jpa.User;
import ru.ima.model.jpa.UserProject;
import ru.ima.repo.ProjectRepo;
import ru.ima.repo.TasksRepo;
import ru.ima.repo.UserProjectRepo;

import java.io.IOException;
import java.util.*;

@Service
public class GitlabIntegrationService {
    private final ProjectRepo projectRepo;
    private final UserProjectRepo userProjectRepo;
    private final TasksRepo tasksRepo;

    public GitlabIntegrationService(ProjectRepo projectRepo, UserProjectRepo userProjectRepo, TasksRepo tasksRepo) {
        this.projectRepo = projectRepo;
        this.userProjectRepo = userProjectRepo;
        this.tasksRepo = tasksRepo;
    }

    @Transactional
    public List<Project> integrateProjectsAndIssues(User user, String token) throws IOException {
        GitlabAPI connect = GitlabAPI.connect("https://gitlab.com", token);
        List<Project> projects = this.integrateProjects(connect, user);
        this.integrateIssues(connect, user, projects);
        return projects;
    }

    private List<Project> integrateProjects(GitlabAPI connect, User user) throws IOException {
        List<GitlabProject> ownedProjects = connect.getOwnedProjects();
        List<Project> projectsToSave = new ArrayList<>();
        List<Project> exists = projectRepo.findMy(user.getId());
        for (GitlabProject ownedProject : ownedProjects) {
            if(exists.stream().noneMatch(p->p.getGitlabId()!=null && p.getGitlabId().equals(ownedProject.getId()))) {
                Project project = new Project();
                project.setName(ownedProject.getNameWithNamespace());
                project.setGitlabId(ownedProject.getId());
                project.setGitlabLink(ownedProject.getWebUrl());
                projectsToSave.add(project);
            }
        }
        List<Project> saved = projectRepo.saveAll(projectsToSave);
        List<UserProject> participantsToSave = new ArrayList<>();
        for (Project project : saved) {
            UserProject participant = new UserProject();
            participant.setProjectId(project.getId());
            participant.setUserId(user.getId());
            participantsToSave.add(participant);
            project.setUserProjects(List.of(participant));
        }
        userProjectRepo.saveAll(participantsToSave);
        return saved;
    }

    private List<Task> integrateIssues(GitlabAPI connect, User user, List<Project> projects) throws IOException {
        List<Task> toSave = new ArrayList<>();
        for (Project project : projects) {
            if(project.getGitlabId() != null) {
                List<GitlabIssue> issues = connect.getIssues(project.getGitlabId());
                for (GitlabIssue issue : issues) {
                    Task task = new Task();
                    task.setCreator(user.getId());
                    task.setProjectId(project.getId());
                    task.setName(issue.getTitle());
                    if(issue.getDescription() != null) {
                        task.setDescription(issue.getDescription());
                    }
                    task.setGitlabId(issue.getId());
                    if(project.getGitlabLink() != null) {
                        task.setGitlabLink(project.getGitlabLink() + "/-/issues/"+issue.getIid());
                    }
                    if(issue.getState() != null && issue.getState().equals("opened")) {
                        task.setStatus(TaskStatus.OPEN);
                    } else {
                        task.setStatus(TaskStatus.DONE);
                    }
                    toSave.add(task);
                }
            }
        }
        return tasksRepo.saveAll(toSave);
    }
}

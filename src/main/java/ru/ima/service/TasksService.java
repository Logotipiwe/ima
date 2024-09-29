package ru.ima.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ima.model.jpa.Project;
import ru.ima.model.jpa.Task;
import ru.ima.model.jpa.User;
import ru.ima.repo.ProjectRepo;
import ru.ima.repo.TasksRepo;

@Slf4j
@Service
public class TasksService {
    private final TasksRepo tasksRepo;
    private final ProjectRepo projectRepo;
    private final GitlabIntegrationService gitlabIntegrationService;

    public TasksService(TasksRepo tasksRepo, ProjectRepo projectRepo, GitlabIntegrationService gitlabIntegrationService) {
        this.tasksRepo = tasksRepo;
        this.projectRepo = projectRepo;
        this.gitlabIntegrationService = gitlabIntegrationService;
    }

    public Task create(User user, Task task) {
        task.setCreator(user.getId());

        try {
            Project project = projectRepo.getReferenceById(task.getProjectId());
            if(project.getGitlabId() != null) {
                gitlabIntegrationService.createIssueFromTask(user, task, project);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return tasksRepo.save(task);
    }
}

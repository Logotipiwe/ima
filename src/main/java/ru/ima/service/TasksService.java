package ru.ima.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        return tasksRepo.save(task);
    }
}

package ru.ima.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ima.model.jpa.Task;
import ru.ima.model.jpa.User;
import ru.ima.repo.TasksRepo;
import ru.ima.service.GitlabIntegrationService;
import ru.ima.service.TasksService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/ima/api/tasks")
public class TasksController {
    private final TasksRepo tasksRepo;
    private final TasksService tasksService;
    private final GitlabIntegrationService gitlabIntegrationService;

    public TasksController(TasksRepo tasksRepo, TasksService tasksService, GitlabIntegrationService gitlabIntegrationService) {
        this.tasksRepo = tasksRepo;
        this.tasksService = tasksService;
        this.gitlabIntegrationService = gitlabIntegrationService;
    }

    @PostMapping
    public ResponseEntity create(
            @AuthenticationPrincipal User user,
            @RequestBody Task task
    ) {
        return ResponseEntity.ok(tasksService.create(user, task));
    }

    @PutMapping
    public ResponseEntity update(
            @AuthenticationPrincipal User user,
            @RequestBody Task task
    ) {
        if(user.getGitlabToken() != null && task.getName() != null) {
            try {
                gitlabIntegrationService.createIssueIfNeeded(user, task);
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }

        tasksRepo.save(task);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity delete(
            @PathVariable("taskId") UUID taskId
    ){
        tasksRepo.deleteById(taskId);
        return ResponseEntity.ok().build();
    }
}

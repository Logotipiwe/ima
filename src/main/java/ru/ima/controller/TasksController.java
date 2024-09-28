package ru.ima.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ima.model.jpa.Task;
import ru.ima.model.jpa.User;
import ru.ima.repo.TasksRepo;

import java.util.UUID;

@RestController
@RequestMapping("/ima/api/tasks")
public class TasksController {
    private final TasksRepo tasksRepo;

    public TasksController(TasksRepo tasksRepo) {
        this.tasksRepo = tasksRepo;
    }

    @PostMapping
    public ResponseEntity create(
            @AuthenticationPrincipal User user,
            @RequestBody Task task
    ) {
        task.setCreator(user.getId());
        tasksRepo.save(task);
        return ResponseEntity.ok(task);
    }

    @PutMapping
    public ResponseEntity update(
            @AuthenticationPrincipal User user,
            @RequestBody Task task
    ) {
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

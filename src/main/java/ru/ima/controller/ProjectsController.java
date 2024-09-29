package ru.ima.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ima.model.jpa.Project;
import ru.ima.model.jpa.Task;
import ru.ima.model.jpa.User;
import ru.ima.model.jpa.UserProject;
import ru.ima.repo.ProjectRepo;
import ru.ima.repo.TasksRepo;
import ru.ima.repo.UserProjectRepo;
import ru.ima.service.GitlabIntegrationService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/ima/api/projects")
public class ProjectsController {
    private final ProjectRepo projectRepo;
    private final UserProjectRepo userProjectRepo;
    private final TasksRepo tasksRepo;
    private final GitlabIntegrationService gitlabIntegrationService;

    public ProjectsController(
            ProjectRepo projectRepo, UserProjectRepo userProjectRepo, TasksRepo tasksRepo, GitlabIntegrationService gitlabIntegrationService
    ){

        this.projectRepo = projectRepo;
        this.userProjectRepo = userProjectRepo;
        this.tasksRepo = tasksRepo;
        this.gitlabIntegrationService = gitlabIntegrationService;
    }

    @PostMapping
    public ResponseEntity create(
            @AuthenticationPrincipal User user,
            @RequestBody Project project
    ){
        project = projectRepo.save(project);
        UserProject userProject = new UserProject();
        userProject.setProjectId(project.getId());
        userProject.setUserId(user.getId());
        userProjectRepo.save(userProject);

        if(user.getGitlabToken() != null){
            try {
                gitlabIntegrationService.createProject(user, project);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return ResponseEntity.ok().body(project);
    }

    @GetMapping
    public ResponseEntity getMyProjects(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(projectRepo.findMy(user.getId()));
    }

    @PostMapping("/{projectId}/invite/{inviteeId}")
    public ResponseEntity inviteUser(
        @AuthenticationPrincipal User user,
        @PathVariable("projectId") UUID projectId,
        @PathVariable("inviteeId") Integer inviteeId
    ){
        if(!projectRepo.isUserInProject(user.getId(), projectId)) {
            throw new RuntimeException("You are not in this project");
        }

        UserProject userProject = new UserProject();
        userProject.setProjectId(projectId);
        userProject.setUserId(inviteeId);
        userProjectRepo.save(userProject);
        return ResponseEntity.ok(userProject);
    }

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity getAll(
            @AuthenticationPrincipal User user,
            @PathVariable("projectId") UUID projectId
    ) {
        List<Task> tasks = tasksRepo.findAllByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity delete(
            @AuthenticationPrincipal User user,
            @PathVariable("projectId") UUID projectId
    ) {
        Project found = projectRepo.getReferenceById(projectId);
        Boolean userIsInProject = found.getUserProjects().stream().anyMatch(up -> up.getUserId().equals(user.getId()));
        if(userIsInProject) {
            projectRepo.delete(found);
            return ResponseEntity.ok().build();
        } else {
            throw new RuntimeException("Project not found for this user");
        }
    }
}

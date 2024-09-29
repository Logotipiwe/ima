package ru.ima.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ima.model.jpa.User;
import ru.ima.repo.UserRepo;
import ru.ima.service.AuthenticationService;

import java.util.UUID;

@RestController
@RequestMapping("/ima/api/users")
public class UsersController {
    private final UserRepo userRepo;
    private final AuthenticationService authenticationService;

    public UsersController(
            UserRepo userRepo, AuthenticationService authenticationService
    ){

        this.userRepo = userRepo;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity getAll(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @GetMapping("/verify")
    public ResponseEntity verify(
            @AuthenticationPrincipal User user,
            @RequestParam("code") UUID code
    ){
        if(user.getConfirmationCode() != null && user.getConfirmationCode().equals(code)) {
            authenticationService.verifyUser(user);
            return ResponseEntity.ok().build(); // TODO редирект
        }
        throw new RuntimeException("No user with such code");
    }

    @GetMapping("/me")
    public ResponseEntity getMe(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(user);
    }

    @PostMapping("/tokens")
    public ResponseEntity setTokens(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "githubToken", required = false) String githubToken,
            @RequestParam(value = "gitlabToken", required = false) String gitlabToken
    ) {
        if(gitlabToken != null) {
            user.setGitlabToken(Strings.isBlank(gitlabToken) ? null : gitlabToken);
        }
        if(githubToken != null) {
            user.setGithubToken(Strings.isBlank(githubToken) ? null : githubToken);
        }
        return ResponseEntity.ok(userRepo.save(user));
    }
}

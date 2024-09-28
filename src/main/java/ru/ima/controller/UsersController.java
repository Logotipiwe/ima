package ru.ima.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ima.model.jpa.User;
import ru.ima.repo.UserRepo;

@RestController
@RequestMapping("/ima/api/users")
public class UsersController {
    private final UserRepo userRepo;

    public UsersController(
            UserRepo userRepo
    ){

        this.userRepo = userRepo;
    }

    @GetMapping
    public ResponseEntity getAll(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userRepo.findAll());
    }

}

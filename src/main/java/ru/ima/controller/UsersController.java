package ru.ima.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ima.model.jpa.User;
import ru.ima.repo.UserRepo;
import ru.ima.service.AuthenticationService;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
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
}

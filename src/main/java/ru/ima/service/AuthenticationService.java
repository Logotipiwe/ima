package ru.ima.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ima.model.jpa.User;
import ru.ima.model.dto.LoginUserDto;
import ru.ima.model.dto.RegisterUserDto;
import ru.ima.repo.UserRepo;

import java.util.UUID;

@Service
public class AuthenticationService {
    public static final String AUTH_COOKIE_NAME = "jwt";

    private final UserRepo userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public AuthenticationService(UserRepo userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public User signup(RegisterUserDto input) {
        User user = new User(
                input.getFullName(),
                input.getEmail(),
                passwordEncoder.encode(input.getPassword()),
                UUID.randomUUID()
        );
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User verifyUser(User user) {
        user.setConfirmationCode(null);
        user.setVerified(true);
        return userRepo.save(user);
    }
}

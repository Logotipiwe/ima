package ru.ima.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ima.model.jpa.User;
import ru.ima.model.dto.LoginResponse;
import ru.ima.model.dto.LoginUserDto;
import ru.ima.model.dto.RegisterUserDto;
import ru.ima.service.AuthenticationService;
import ru.ima.service.JwtService;
import ru.ima.service.mail.MailService;

@Slf4j
@RequestMapping("/ima/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final MailService mailService;
    @Value("${ima.mail-verification}")
    private Boolean verificationEnabled;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, MailService mailService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.mailService = mailService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(
            @RequestBody RegisterUserDto registerUserDto,
            HttpServletRequest request
    ) {
        User registeredUser = authenticationService.signup(registerUserDto);

        if(verificationEnabled) {
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            try {
                mailService.sendVerificationEmail(registeredUser, serverName + ":" + serverPort);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } else {
            registeredUser = authenticationService.verifyUser(registeredUser);
        }
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginUserDto loginUserDto,
            HttpServletResponse response
    ) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        Cookie cookie = new Cookie(AuthenticationService.AUTH_COOKIE_NAME, loginResponse.getToken());
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponse);
    }
}

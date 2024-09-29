package ru.ima.service.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.ima.model.jpa.User;

@Slf4j
@Primary
@Service
@Profile("test")
public class MockMailService implements MailService{
    @Override
    public void sendVerificationEmail(User user, String siteURL) {
        log.info("Sent mail to {}", user.getEmail());
    }
}

package ru.ima.service.mail;

import jakarta.mail.MessagingException;
import ru.ima.model.jpa.User;

import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;
}

package ru.ima.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.ima.model.jpa.User;

import java.io.UnsupportedEncodingException;

@Profile("!test")
@Service
public class IcmpMailService implements MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public IcmpMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = fromEmail;
        String senderName = "Сервис IMA";
        String subject = "Регистрация в IMA - подтвердите аккаунт";
        String content = "Уважаемый " + user.getFullName() + ",<br>"
                + "Пожалуйста, пройдите по ссылке ниже для подтверждения аккаунта:<br>"
                + "<h3><a href=\"URL\" target=\"_self\">URL</a></h3>"
                + "Спасибо,<br>"
                + "Ваш IMA.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());
        String verifyURL = siteURL + "/ima/api/users/verify?code=" + user.getConfirmationCode();

        content = content.replaceAll("URL", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }
}

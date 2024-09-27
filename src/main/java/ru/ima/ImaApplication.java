package ru.ima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@ServletComponentScan
@SpringBootApplication
public class ImaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImaApplication.class, args);
    }

}

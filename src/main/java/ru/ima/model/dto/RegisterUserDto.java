package ru.ima.model.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private final String email;
    private final String password;
    private final String fullName;
}
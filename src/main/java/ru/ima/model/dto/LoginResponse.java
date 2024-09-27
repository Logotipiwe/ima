package ru.ima.model.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private final String token;
    private final Long expiresTime;
}
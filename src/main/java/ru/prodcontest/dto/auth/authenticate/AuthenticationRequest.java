package ru.prodcontest.dto.auth.authenticate;

import lombok.*;

@Getter
@Setter
public class AuthenticationRequest {
    private String login;
    private String password;
}

package ru.prodcontest.dto.auth.authenticate;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private String token;
}

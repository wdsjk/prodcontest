package ru.prodcontest.dto.auth.register;

import lombok.*;

@Getter
@Setter
public class RegisterRequest {
    public String login;

    public String email;

    public String password;

    public String countryCode;

    public Boolean isPublic;

    public String phone;

    public String image;
}

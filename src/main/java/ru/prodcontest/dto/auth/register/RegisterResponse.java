package ru.prodcontest.dto.auth.register;

import lombok.*;
import ru.prodcontest.dto.profile.Profile;

@AllArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    Profile profile;
}

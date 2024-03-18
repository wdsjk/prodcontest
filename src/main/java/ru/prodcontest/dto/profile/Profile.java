package ru.prodcontest.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.prodcontest.domain.user.User;

@Getter
@Setter
public class Profile {
    public Profile(User user) {
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.countryCode = user.getCountryCode();
        this.isPublic = user.getIsPublic();
        this.phone = user.getPhone();
        this.image = user.getImage();
    }

    public String login;

    public String email;

    public String countryCode;

    public Boolean isPublic;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String image;
}

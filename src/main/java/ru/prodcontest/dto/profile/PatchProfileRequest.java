package ru.prodcontest.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchProfileRequest {
    private String countryCode;

    private Boolean isPublic;

    private String phone;

    private String image;
}

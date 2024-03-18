package ru.prodcontest.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.prodcontest.dto.profile.Profile;

import ru.prodcontest.exception.profile.ProfileAccessDeniedException;
import ru.prodcontest.exception.user.CustomAccessDeniedException;
import ru.prodcontest.exception.user.UserNotFoundNotFoundException;
import ru.prodcontest.service.profile.ProfileService;

@RestController
@RequestMapping("/api/profiles")
public class ProfilesController {
    private final ProfileService profileService;

    @Autowired
    public ProfilesController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{login}")
    public ResponseEntity<Profile> getProfile(@RequestHeader(name = "Authorization") String token, @PathVariable String login) throws ProfileAccessDeniedException, CustomAccessDeniedException, UserNotFoundNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(profileService.getProfileByLogin(token, login));
    }
}

package ru.prodcontest.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.prodcontest.dto.profile.PatchProfileRequest;
import ru.prodcontest.dto.profile.Profile;
import ru.prodcontest.dto.profile.updatePassword.UpdatePasswordRequest;
import ru.prodcontest.dto.StatusOkResponse;

import ru.prodcontest.exception.country.CountryNotFoundException;
import ru.prodcontest.exception.user.CustomAccessDeniedException;
import ru.prodcontest.exception.user.InvalidPasswordException;
import ru.prodcontest.exception.user.UserRegistrationException;

import ru.prodcontest.service.profile.ProfileService;
import ru.prodcontest.service.user.UserService;

@RestController
@RequestMapping("/api/me")
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;

    @Autowired
    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Profile> myProfile(@RequestHeader(name = "Authorization") String token) throws CustomAccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(new Profile(userService.getUserByToken(token)));
    }

    @PatchMapping("/profile")
    public ResponseEntity<Profile> updateUser(@RequestHeader(name = "Authorization") String token, @RequestBody PatchProfileRequest request) throws CountryNotFoundException, UserRegistrationException, CustomAccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(profileService.updateProfile(request, userService.getUserByToken(token)));
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<StatusOkResponse> updatePassword(@RequestHeader(name = "Authorization") String token, @RequestBody UpdatePasswordRequest request) throws UserRegistrationException, InvalidPasswordException, CustomAccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(profileService.updatePassword(request, userService.getUserByToken(token)));
    }
}

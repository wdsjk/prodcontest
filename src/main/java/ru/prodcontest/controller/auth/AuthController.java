package ru.prodcontest.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.prodcontest.dto.auth.authenticate.AuthenticationRequest;
import ru.prodcontest.dto.auth.authenticate.AuthenticationResponse;
import ru.prodcontest.dto.auth.register.RegisterRequest;
import ru.prodcontest.dto.auth.register.RegisterResponse;
import ru.prodcontest.exception.country.CountryNotFoundException;
import ru.prodcontest.exception.user.*;

import ru.prodcontest.service.auth.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> addUser(@RequestBody RegisterRequest request) throws UserAlreadyExistsException, UserRegistrationException, CountryNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signUserIn(@RequestBody AuthenticationRequest request) throws UserNotFoundUnauthorizedException, InvalidPasswordException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(authService.authenticate(request));
    }
}
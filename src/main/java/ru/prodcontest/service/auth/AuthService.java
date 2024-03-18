package ru.prodcontest.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.prodcontest.JWT.JwtService;
import ru.prodcontest.exception.user.*;
import ru.prodcontest.utils.UserValidation;

import ru.prodcontest.domain.user.Role;
import ru.prodcontest.domain.user.User;

import ru.prodcontest.dto.auth.authenticate.AuthenticationRequest;
import ru.prodcontest.dto.auth.authenticate.AuthenticationResponse;
import ru.prodcontest.dto.auth.register.RegisterRequest;
import ru.prodcontest.dto.auth.register.RegisterResponse;
import ru.prodcontest.dto.profile.Profile;

import ru.prodcontest.exception.country.CountryNotFoundException;
import ru.prodcontest.repository.user.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final UserValidation userValidation;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       UserValidation userValidation,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userValidation = userValidation;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public RegisterResponse register(RegisterRequest request) throws CountryNotFoundException, UserAlreadyExistsException, UserRegistrationException {
        User user = new User(
                request.getLogin(),
                request.getEmail(),
                request.getPassword(),
                request.getCountryCode(),
                request.getIsPublic(),
                request.getPhone(),
                request.getImage()
        );

        if (validate(user)) {
            if (userNotExists(user)) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRole(Role.USER);

                userRepository.save(user);
                return new RegisterResponse(new Profile(user));
            } else
                throw new UserAlreadyExistsException();
        } else
            throw new UserRegistrationException();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundUnauthorizedException, InvalidPasswordException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLogin(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new InvalidPasswordException();
        }

        User user = userRepository.findByLogin(request.getLogin()).orElseThrow(UserNotFoundUnauthorizedException::new);
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    private boolean userNotExists(User user) {
        if (user.getPhone() == null)
            return userRepository.findByLoginOrEmail(
                    user.getLogin(),
                    user.getEmail()
            ).orElse(null) == null;

        return userRepository.findByLoginOrEmailOrPhone(
                user.getLogin(),
                user.getEmail(),
                user.getPhone()
        ).orElse(null) == null;
    }

    private boolean validate(User user) throws CountryNotFoundException {
        return userValidation.validate(user);
    }
}

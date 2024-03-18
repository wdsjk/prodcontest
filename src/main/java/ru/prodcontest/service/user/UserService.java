package ru.prodcontest.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.prodcontest.JWT.JwtService;
import ru.prodcontest.domain.friend.Friend;
import ru.prodcontest.domain.likes_and_dislikes.Dislike;
import ru.prodcontest.domain.likes_and_dislikes.Like;
import ru.prodcontest.domain.user.User;

import ru.prodcontest.dto.profile.PatchProfileRequest;
import ru.prodcontest.dto.profile.updatePassword.UpdatePasswordRequest;
import ru.prodcontest.dto.StatusOkResponse;

import ru.prodcontest.exception.country.CountryNotFoundException;
import ru.prodcontest.exception.user.*;

import ru.prodcontest.repository.user.UserRepository;
import ru.prodcontest.utils.UserValidation;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserValidation userValidation;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UserValidation userValidation) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userValidation = userValidation;
    }

    public User updateUser(PatchProfileRequest request, User userToUpdate) throws CountryNotFoundException, UserRegistrationException {
        if (validatePatch(request)) {
            if (null != request.getCountryCode())
                userToUpdate.setCountryCode(request.getCountryCode());
            if (null != request.getIsPublic())
                userToUpdate.setIsPublic(request.getIsPublic());
            if (null != request.getPhone())
                userToUpdate.setPhone(request.getPhone());
            if (null != request.getImage())
                userToUpdate.setImage(request.getImage());

            userRepository.save(userToUpdate);

            return userToUpdate;
        } else
            throw new UserRegistrationException();
    }

    public StatusOkResponse updatePassword(UpdatePasswordRequest request, User user) throws UserRegistrationException, InvalidPasswordException {
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            if (validatePassword(request.getNewPassword())) {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);

                return new StatusOkResponse("ok");
            }

            throw new UserRegistrationException();
        }

        throw new InvalidPasswordException();
    }

    public User getUserByToken(String token) throws CustomAccessDeniedException {
        return userRepository.findByLogin(jwtService.extractLogin(token.substring(7))).orElseThrow(CustomAccessDeniedException::new);
    }

    public User getUserByLogin(String login) throws UserNotFoundNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(UserNotFoundNotFoundException::new);
    }

    private boolean validatePatch(PatchProfileRequest request) throws CountryNotFoundException {
        return userValidation.validatePatch(request);
    }

    private boolean validatePassword(String password) {
        return userValidation.validatePassword(password);
    }

    public void addFriend(Friend friend, User user) {
        if (null == user.getFriends())
            user.setFriends(List.of(friend));
        else
            user.getFriends().add(friend);

        userRepository.save(user);
    }

    public void removeFriend(User user, Friend friend) {
        if (null != user.getFriends()) {
            user.getFriends().remove(friend);
            userRepository.save(user);
        }
    }

    public void updateLikes(User user, Like like) {
        if (null == user.getLikes())
            user.setLikes(List.of(like));
        else
            user.getLikes().add(like);

        userRepository.save(user);
    }

    public void updateDislikes(User user, Dislike dislike) {
        if (null == user.getDislikes())
            user.setDislikes(List.of(dislike));
        else
            user.getDislikes().add(dislike);

        userRepository.save(user);
    }
}

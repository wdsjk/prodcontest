package ru.prodcontest.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prodcontest.domain.user.User;
import ru.prodcontest.dto.profile.PatchProfileRequest;
import ru.prodcontest.dto.profile.Profile;
import ru.prodcontest.dto.profile.updatePassword.UpdatePasswordRequest;
import ru.prodcontest.dto.StatusOkResponse;

import ru.prodcontest.exception.country.CountryNotFoundException;
import ru.prodcontest.exception.profile.ProfileAccessDeniedException;
import ru.prodcontest.exception.user.*;
import ru.prodcontest.service.friend.FriendsService;
import ru.prodcontest.service.user.UserService;

@Service
public class ProfileService {
    private final UserService userService;
    private final FriendsService friendsService;

    @Autowired
    public ProfileService(UserService userService, FriendsService friendsService) {
        this.userService = userService;
        this.friendsService = friendsService;
    }

    public Profile updateProfile(PatchProfileRequest request, User user) throws CountryNotFoundException, UserRegistrationException {
        User updatedUser = userService.updateUser(request, user);

        return new Profile(updatedUser);
    }

    public StatusOkResponse updatePassword(UpdatePasswordRequest request, User user) throws UserRegistrationException, InvalidPasswordException {
        return userService.updatePassword(request, user);
    }

    public Profile getProfileByLogin(String token, String login) throws ProfileAccessDeniedException, CustomAccessDeniedException, UserNotFoundNotFoundException {
        User userSendingRequest = userService.getUserByToken(token);

        User userToCheck = userService.getUserByLogin(login);

        if (!userSendingRequest.equals(userToCheck) && (!userToCheck.getIsPublic() && !friendsService.isFriends(userSendingRequest, userToCheck)))
            throw new ProfileAccessDeniedException();

        return new Profile(userToCheck);
    }
}

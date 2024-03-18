package ru.prodcontest.service.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prodcontest.domain.friend.Friend;
import ru.prodcontest.domain.user.User;

import ru.prodcontest.dto.StatusOkResponse;
import ru.prodcontest.dto.friends.FriendPostRequest;

import ru.prodcontest.dto.friends.FriendResponse;
import ru.prodcontest.exception.user.CustomAccessDeniedException;
import ru.prodcontest.exception.user.UserNotFoundNotFoundException;
import ru.prodcontest.repository.friend.FriendsRepository;
import ru.prodcontest.service.user.UserService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FriendsService {
    private final UserService userService;
    private final FriendsRepository friendsRepository;

    @Autowired
    public FriendsService(UserService userService, FriendsRepository friendsRepository) {
        this.userService = userService;
        this.friendsRepository = friendsRepository;
    }

    public StatusOkResponse add(FriendPostRequest request, String token) throws CustomAccessDeniedException, UserNotFoundNotFoundException {
        User userThatWantFriends = userService.getUserByToken(token);

        Friend friend = new Friend(
                userThatWantFriends.getLogin(),
                userService.getUserByLogin(request.getLogin()).getLogin());

        if (userThatWantFriends.getLogin().equals(request.getLogin()) ||
                userThatWantFriends.getFriends().contains(friend))
            return new StatusOkResponse("ok");

        friendsRepository.save(friend);
        userService.addFriend(friend, userThatWantFriends);

        return new StatusOkResponse("ok");
    }

    public StatusOkResponse remove(FriendPostRequest request, String token) throws CustomAccessDeniedException, UserNotFoundNotFoundException {
        User userThatDoNotWantFriends = userService.getUserByToken(token);

        List<Friend> friends = friendsRepository.findByLoginWhoAddedFriend(userThatDoNotWantFriends.getLogin()).orElse(null);

        if (null == friends)
            return new StatusOkResponse("ok");

        for (Friend friend : friends) {
            if (friend.getFriendLogin().equals(request.getLogin())) {
                userService.removeFriend(userThatDoNotWantFriends, friend);
                friendsRepository.delete(friend);
            }
        }

        return new StatusOkResponse("ok");
    }

    // is userToCheck has friend userSendingRequest
    public boolean isFriends(User userSendingRequest, User userToCheck) {
        List<Friend> friendsOfUserToCheck = friendsRepository.findByLoginWhoAddedFriend(userToCheck.getLogin()).orElse(null);

        if (null == friendsOfUserToCheck)
            return false;

        for (Friend friend : friendsOfUserToCheck) {
            if (friend.getFriendLogin().equals(userSendingRequest.getLogin()))
                return true;
        }

        return false;
    }

    public List<FriendResponse> getAllFriendsFromUser(String token, Integer limit, Integer offset) throws CustomAccessDeniedException {
        User user = userService.getUserByToken(token);

        List<Friend> friends = friendsRepository.findByLoginWhoAddedFriend(user.getLogin()).orElse(List.of())
                .stream().sorted(Comparator.comparing(Friend::getAddedAt)).toList();

        List<FriendResponse> responseFriends = new ArrayList<>();

        for (int i = friends.size()-1; i >= 0; i--) {
            responseFriends.add(new FriendResponse(friends.get(i)));
        }

        if (limit >= responseFriends.size() && offset <= responseFriends.size())
            return responseFriends.subList(offset, responseFriends.size());
        else if (limit < responseFriends.size() && offset < limit && offset >= 0)
            return responseFriends.subList(offset, limit);

        throw new CustomAccessDeniedException();
    }
}

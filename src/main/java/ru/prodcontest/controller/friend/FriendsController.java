package ru.prodcontest.controller.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.prodcontest.dto.StatusOkResponse;
import ru.prodcontest.dto.friends.FriendPostRequest;

import ru.prodcontest.dto.friends.FriendResponse;
import ru.prodcontest.exception.user.CustomAccessDeniedException;
import ru.prodcontest.exception.user.UserNotFoundNotFoundException;

import ru.prodcontest.service.friend.FriendsService;

import java.util.Collection;

@RestController
@RequestMapping("/api/friends")
public class FriendsController {
    private final FriendsService friendsService;

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @GetMapping
    public ResponseEntity<Collection<FriendResponse>> getFriends(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "limit") Integer limit, @RequestParam(name = "offset") Integer offset) throws CustomAccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(friendsService.getAllFriendsFromUser(token, limit, offset));
    }

    @PostMapping("/add")
    public ResponseEntity<StatusOkResponse> addFriend(@RequestHeader(name = "Authorization") String token, @RequestBody FriendPostRequest request) throws CustomAccessDeniedException, UserNotFoundNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(friendsService.add(request, token));
    }

    @PostMapping("/remove")
    public ResponseEntity<StatusOkResponse> removeFriend(@RequestHeader(name = "Authorization") String token, @RequestBody FriendPostRequest request) throws CustomAccessDeniedException, UserNotFoundNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(friendsService.remove(request, token));
    }
}

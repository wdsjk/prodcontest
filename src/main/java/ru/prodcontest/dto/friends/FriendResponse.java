package ru.prodcontest.dto.friends;

import lombok.Getter;
import lombok.Setter;
import ru.prodcontest.domain.friend.Friend;

import java.util.Date;

@Getter
@Setter
public class FriendResponse {
    private String login;

    private Date addedAt;

    public FriendResponse(Friend friend) {
        this.login = friend.getFriendLogin();
        this.addedAt = friend.getAddedAt();
    }
}

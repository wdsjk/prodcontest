package ru.prodcontest.repository.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prodcontest.domain.friend.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friend, Long> {
    Optional<List<Friend>> findByLoginWhoAddedFriend(String login);
}

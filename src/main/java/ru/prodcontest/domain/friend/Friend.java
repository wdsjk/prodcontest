package ru.prodcontest.domain.friend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.prodcontest.domain.user.User;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "friends")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Friend {
    @Id
    @SequenceGenerator(sequenceName = "friends_sequence", name = "friends_sequence", initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String loginWhoAddedFriend;

    private Date addedAt;

    private String friendLogin;

    @ManyToMany(mappedBy = "friends")
    private List<User> whomFriends;

    public Friend(String loginWhoAddedFriend, String friendLogin) {
        this.loginWhoAddedFriend = loginWhoAddedFriend;
        this.addedAt = new Date();
        this.friendLogin = friendLogin;
    }
}

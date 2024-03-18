package ru.prodcontest.domain.likes_and_dislikes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.prodcontest.domain.user.User;

@Entity
@Table(name = "dislikes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dislike {
    @Id
    @Column(unique = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String postId;

    public Dislike(User user, String postId) {
        this.user = user;
        this.postId = postId;
    }
}

package ru.prodcontest.domain.likes_and_dislikes;

import jakarta.persistence.*;
import lombok.*;
import ru.prodcontest.domain.user.User;

@Entity
@Table(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Like {
    @Id
    @Column(unique = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String postId;

    public Like(User user, String postId) {
        this.user = user;
        this.postId = postId;
    }
}

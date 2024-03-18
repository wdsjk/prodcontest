package ru.prodcontest.repository.likes_and_dislikes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prodcontest.domain.likes_and_dislikes.Like;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Like, String> {
}

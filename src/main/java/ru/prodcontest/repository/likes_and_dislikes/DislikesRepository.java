package ru.prodcontest.repository.likes_and_dislikes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prodcontest.domain.likes_and_dislikes.Dislike;

public interface DislikesRepository extends JpaRepository<Dislike, String> {
}

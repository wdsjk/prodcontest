package ru.prodcontest.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prodcontest.domain.post.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {
    Optional<List<Post>> findAllByAuthorLogin(String authorLogin);
}

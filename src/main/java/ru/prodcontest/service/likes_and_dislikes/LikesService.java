package ru.prodcontest.service.likes_and_dislikes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prodcontest.domain.likes_and_dislikes.Like;
import ru.prodcontest.repository.likes_and_dislikes.LikesRepository;

@Service
public class LikesService {
    private final LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    public void add(Like like) {
        like.setId(like.getUser().getLogin()+like.getPostId());
        likesRepository.save(like);
    }

    public boolean checkIfLikeAlreadyGiven(String userLogin, String postId) {
        return null != likesRepository.findById(userLogin+postId).orElse(null);
    }

    public Like getLike(String userLogin, String postId) {
        return likesRepository.findById(userLogin+postId).orElse(null);
    }

    public void remove(Like like) {
        if (null != like)
            likesRepository.delete(like);
    }
}

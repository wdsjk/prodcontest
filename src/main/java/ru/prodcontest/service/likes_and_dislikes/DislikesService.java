package ru.prodcontest.service.likes_and_dislikes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prodcontest.domain.likes_and_dislikes.Dislike;
import ru.prodcontest.repository.likes_and_dislikes.DislikesRepository;

@Service
public class DislikesService {
    private final DislikesRepository dislikesRepository;

    @Autowired
    public DislikesService(DislikesRepository dislikesRepository) {
        this.dislikesRepository = dislikesRepository;
    }

    public void add(Dislike dislike) {
        dislike.setId(dislike.getUser().getLogin()+dislike.getPostId());
        dislikesRepository.save(dislike);
    }

    public boolean checkIfDislikeAlreadyGiven(String userLogin, String postId) {
        return null != dislikesRepository.findById(userLogin+postId).orElse(null);
    }

    public Dislike getDislike(String userLogin, String postId) {
        return dislikesRepository.findById(userLogin+postId).orElse(null);
    }

    public void remove(Dislike dislike) {
        if (null != dislike)
            dislikesRepository.delete(dislike);
    }
}

package ru.prodcontest.service.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prodcontest.domain.likes_and_dislikes.Dislike;
import ru.prodcontest.domain.likes_and_dislikes.Like;
import ru.prodcontest.domain.post.Post;
import ru.prodcontest.domain.user.User;

import ru.prodcontest.dto.post.PostRequest;
import ru.prodcontest.dto.post.PostResponse;

import ru.prodcontest.exception.post.PostNotFoundException;
import ru.prodcontest.exception.user.CustomAccessDeniedException;
import ru.prodcontest.exception.user.UserNotFoundNotFoundException;
import ru.prodcontest.repository.post.PostRepository;
import ru.prodcontest.service.friend.FriendsService;
import ru.prodcontest.service.likes_and_dislikes.DislikesService;
import ru.prodcontest.service.likes_and_dislikes.LikesService;
import ru.prodcontest.service.user.UserService;

import java.util.*;

@Service
public class PostService {
    private final UserService userService;
    private final FriendsService friendsService;

    private final LikesService likesService;
    private final DislikesService dislikesService;

    private final PostRepository postRepository;

    @Autowired
    public PostService(UserService userService, FriendsService friendsService, LikesService likesService, DislikesService dislikesService, PostRepository postRepository) {
        this.userService = userService;
        this.friendsService = friendsService;
        this.likesService = likesService;
        this.dislikesService = dislikesService;
        this.postRepository = postRepository;
    }

    public PostResponse add(String token, PostRequest request) throws CustomAccessDeniedException {
        User author = userService.getUserByToken(token);

        Post newPost = new Post(UUID.randomUUID().toString(), request.getContent(),
                author.getLogin(), request.getTags(),
                new Date(), 0, 0);

        postRepository.save(newPost);

        return new PostResponse(newPost);
    }

    public PostResponse getPostById(String token, String postId) throws CustomAccessDeniedException, PostNotFoundException, UserNotFoundNotFoundException {
        User userThatWantToSee = userService.getUserByToken(token);

        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        User author = userService.getUserByLogin(post.getAuthorLogin());

        if (!author.getIsPublic() &&
                !friendsService.isFriends(userThatWantToSee, author) &&
                !author.getLogin().equals(userThatWantToSee.getLogin()))
            throw new PostNotFoundException();

        return new PostResponse(post);
    }

    public List<PostResponse> getAllMyPosts(String token, Integer limit, Integer offset) throws CustomAccessDeniedException {
        String authorLogin = userService.getUserByToken(token).getLogin();

        List<Post> posts = postRepository.findAllByAuthorLogin(authorLogin).orElse(List.of())
                .stream().sorted(Comparator.comparing(Post::getCreatedAt)).toList();

        List<PostResponse> postResponseList = new ArrayList<>();

        for (int i = posts.size()-1; i >= 0; i--) {
            postResponseList.add(new PostResponse(posts.get(i)));
        }

        if (limit <= 50 && offset >= 0 && limit >= postResponseList.size() && offset <= postResponseList.size())
            return postResponseList.subList(offset, postResponseList.size());
        else if (limit <= 50  && offset >= 0 && limit < postResponseList.size() && offset < limit)
            return postResponseList.subList(offset, limit);

        throw new CustomAccessDeniedException();
    }

    public List<PostResponse> getAllUserPosts(String token, String login, Integer limit, Integer offset) throws CustomAccessDeniedException, UserNotFoundNotFoundException, PostNotFoundException {
        User userThatWantToSee = userService.getUserByToken(token);
        User userThatWantedToBeSeen = userService.getUserByLogin(login);

        if (!userThatWantedToBeSeen.getIsPublic() &&
                !friendsService.isFriends(userThatWantToSee, userThatWantedToBeSeen) &&
                !userThatWantToSee.getLogin().equals(userThatWantedToBeSeen.getLogin()))
            throw new PostNotFoundException();

        List<Post> posts = postRepository.findAllByAuthorLogin(userThatWantedToBeSeen.getLogin()).orElse(List.of())
                .stream().sorted(Comparator.comparing(Post::getCreatedAt)).toList();

        List<PostResponse> postResponseList = new ArrayList<>();

        for (int i = posts.size()-1; i >= 0; i--) {
            postResponseList.add(new PostResponse(posts.get(i)));
        }

        if (limit <= 50 && offset >= 0 && limit >= postResponseList.size() && offset <= postResponseList.size())
            return postResponseList.subList(offset, postResponseList.size());
        else if (limit <= 50 && offset >= 0 && limit < postResponseList.size() && offset < limit)
            return postResponseList.subList(offset, limit);

        return postResponseList;
    }

    public PostResponse like(String token, String postId) throws CustomAccessDeniedException, PostNotFoundException {
        User user = userService.getUserByToken(token);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        Like like = new Like(user, postId);

        if (likesService.checkIfLikeAlreadyGiven(user.getLogin(), postId))
            throw new PostNotFoundException();

        likesService.add(like);
        userService.updateLikes(user, like);

        if (dislikesService.checkIfDislikeAlreadyGiven(user.getLogin(), postId)) {
            dislikesService.remove(dislikesService.getDislike(user.getLogin(), postId));
            post.setDislikesCount(post.getDislikesCount()-1);
        }

        post.setLikesCount(post.getLikesCount()+1);
        postRepository.save(post);

        return new PostResponse(post);
    }

    public PostResponse dislike(String token, String postId) throws CustomAccessDeniedException, PostNotFoundException {
        User user = userService.getUserByToken(token);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        Dislike dislike = new Dislike(user, postId);

        if (dislikesService.checkIfDislikeAlreadyGiven(user.getLogin(), postId))
            throw new PostNotFoundException();

        dislikesService.add(dislike);
        userService.updateDislikes(user, dislike);

        if (likesService.checkIfLikeAlreadyGiven(user.getLogin(), postId)) {
            likesService.remove(likesService.getLike(user.getLogin(), postId));
            post.setLikesCount(post.getLikesCount()-1);
        }

        post.setDislikesCount(post.getDislikesCount()+1);
        postRepository.save(post);

        return new PostResponse(post);
    }
}

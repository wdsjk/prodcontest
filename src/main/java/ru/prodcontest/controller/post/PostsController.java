package ru.prodcontest.controller.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.prodcontest.dto.post.PostRequest;
import ru.prodcontest.dto.post.PostResponse;

import ru.prodcontest.exception.post.PostNotFoundException;
import ru.prodcontest.exception.user.CustomAccessDeniedException;
import ru.prodcontest.exception.user.UserNotFoundNotFoundException;

import ru.prodcontest.service.post.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostService postService;

    @Autowired
    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/new")
    public ResponseEntity<PostResponse> createPost(@RequestHeader(name = "Authorization") String token, @RequestBody PostRequest request) throws CustomAccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(postService.add(token, request));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@RequestHeader(name = "Authorization") String token, @PathVariable String postId) throws UserNotFoundNotFoundException, PostNotFoundException, CustomAccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(postService.getPostById(token, postId));
    }

    @GetMapping("/feed/my")
    public ResponseEntity<List<PostResponse>> getAllMyPosts(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "limit") Integer limit, @RequestParam(name = "offset") Integer offset) throws CustomAccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(postService.getAllMyPosts(token, limit, offset));
    }

    @GetMapping("/feed/{login}")
    public ResponseEntity<List<PostResponse>> getAllUserPosts(@RequestHeader(name = "Authorization") String token, @PathVariable String login, @RequestParam(name = "limit") Integer limit, @RequestParam(name = "offset") Integer offset) throws CustomAccessDeniedException, UserNotFoundNotFoundException, PostNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(postService.getAllUserPosts(token, login, limit, offset));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<PostResponse> giveLike(@RequestHeader(name = "Authorization") String token, @PathVariable String postId) throws CustomAccessDeniedException, PostNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(postService.like(token, postId));
    }

    @PostMapping("/{postId}/dislike")
    public ResponseEntity<PostResponse> giveDislike(@RequestHeader(name = "Authorization") String token, @PathVariable String postId) throws CustomAccessDeniedException, PostNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(postService.dislike(token, postId));
    }
}

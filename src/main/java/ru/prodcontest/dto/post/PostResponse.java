package ru.prodcontest.dto.post;

import lombok.Getter;
import lombok.Setter;
import ru.prodcontest.domain.post.Post;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostResponse {
    private String id;

    private String content;

    private String author;

    private List<String> tags;

    private Date createdAt;

    private Integer likesCount;

    private Integer dislikesCount;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.content = post.getPostContent();
        this.author = post.getAuthorLogin();
        this.tags = post.getTags();
        this.createdAt = post.getCreatedAt();
        this.likesCount = post.getLikesCount();
        this.dislikesCount = post.getDislikesCount();
    }
}

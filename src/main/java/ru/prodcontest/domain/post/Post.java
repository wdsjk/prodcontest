package ru.prodcontest.domain.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String postContent;

    @Column(nullable = false)
    private String authorLogin;

    @Column(nullable = false)
    private List<String> tags;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Integer likesCount;

    @Column(nullable = false)
    private Integer dislikesCount;
}

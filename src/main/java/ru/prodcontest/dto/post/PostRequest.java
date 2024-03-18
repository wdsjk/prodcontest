package ru.prodcontest.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequest {
    private String content;

    private List<String> tags;
}

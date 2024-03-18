package ru.prodcontest.controller.advice.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.prodcontest.exception.ErrorReason;
import ru.prodcontest.exception.post.PostNotFoundException;

@RestControllerAdvice
public class PostsControllerAdvice {
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorReason> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }
}

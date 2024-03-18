package ru.prodcontest.controller.advice.profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.prodcontest.exception.ErrorReason;
import ru.prodcontest.exception.profile.ProfileAccessDeniedException;
import ru.prodcontest.exception.profile.ProfileNotFoundOrAccessDeniedException;
import ru.prodcontest.exception.user.UserRegistrationException;

@RestControllerAdvice
public class ProfileControllerAdvice {
    @ExceptionHandler(ProfileAccessDeniedException.class)
    public ResponseEntity<ErrorReason> handleProfileAccessDeniedException(ProfileAccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }

    @ExceptionHandler(ProfileNotFoundOrAccessDeniedException.class)
    public ResponseEntity<ErrorReason> handleProfileAccessDeniedException(ProfileNotFoundOrAccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<ErrorReason> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(new UserRegistrationException().getErrorReason());
    }
}

package ru.prodcontest.controller.advice.auth;

import org.hibernate.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.prodcontest.exception.ErrorReason;
import ru.prodcontest.exception.user.*;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ErrorReason> handleUserRegistrationException(UserRegistrationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorReason> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<ErrorReason> handleUserAlreadyExistsException(NonUniqueResultException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(new UserAlreadyExistsException().getErrorReason());
    }

    @ExceptionHandler(UserNotFoundUnauthorizedException.class)
    public ResponseEntity<ErrorReason> handleUserAlreadyExistsException(UserNotFoundUnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorReason> handleUserAlreadyExistsException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(new UserNotFoundUnauthorizedException().getErrorReason());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorReason> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ErrorReason> handleAccessDeniedException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(new CustomAccessDeniedException().getErrorReason());
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<ErrorReason> handleCustomAccessDeniedException(CustomAccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }

    @ExceptionHandler(UserNotFoundNotFoundException.class)
    public ResponseEntity<ErrorReason> handleUserNotFoundNotFoundException(UserNotFoundNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }
}

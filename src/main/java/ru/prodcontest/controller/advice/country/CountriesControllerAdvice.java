package ru.prodcontest.controller.advice.country;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.prodcontest.exception.ErrorReason;
import ru.prodcontest.exception.country.CountryNotFoundException;
import ru.prodcontest.exception.country.InvalidRegionException;

@RestControllerAdvice
public class CountriesControllerAdvice {
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorReason> handleCountryNotFoundException(CountryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }

    @ExceptionHandler(InvalidRegionException.class)
    public ResponseEntity<ErrorReason> handleInvalidRegionException(InvalidRegionException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(e.getErrorReason());
    }
}
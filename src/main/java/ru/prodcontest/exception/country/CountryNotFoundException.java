package ru.prodcontest.exception.country;

import ru.prodcontest.exception.ErrorReason;

public class CountryNotFoundException extends Throwable {
    private final String countryCode;

    public CountryNotFoundException(String countryCode) {
        this.countryCode = countryCode;
    }

    public ErrorReason getErrorReason() {
        return new ErrorReason("Страна с указанным кодом не найдена: "+countryCode);
    }
}
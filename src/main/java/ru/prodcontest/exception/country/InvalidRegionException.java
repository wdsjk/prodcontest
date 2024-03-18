package ru.prodcontest.exception.country;

import ru.prodcontest.exception.ErrorReason;

public class InvalidRegionException extends Throwable {
    private final String region;

    public InvalidRegionException(String region) {
        this.region = region;
    }

    public ErrorReason getErrorReason() {
        return new ErrorReason("Введен некорректный регион: "+region);
    }
}

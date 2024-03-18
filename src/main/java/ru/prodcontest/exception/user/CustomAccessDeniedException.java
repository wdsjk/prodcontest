package ru.prodcontest.exception.user;

import ru.prodcontest.exception.ErrorReason;

public class CustomAccessDeniedException extends Throwable {
    public ErrorReason getErrorReason() {
        return new ErrorReason("Переданный токен не существует либо некорректен");
    }
}

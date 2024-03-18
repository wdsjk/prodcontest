package ru.prodcontest.exception.user;

import ru.prodcontest.exception.ErrorReason;

public class UserNotFoundUnauthorizedException extends Throwable {
    public ErrorReason getErrorReason() {
        return new ErrorReason("Пользователь с указанным логином и паролем не найден");
    }
}

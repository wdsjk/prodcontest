package ru.prodcontest.exception.user;

import ru.prodcontest.exception.ErrorReason;

public class UserNotFoundNotFoundException extends Throwable {
    public ErrorReason getErrorReason() {
        return new ErrorReason("Пользователь с указанным логином не найден");
    }
}

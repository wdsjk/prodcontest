package ru.prodcontest.exception.user;

import ru.prodcontest.exception.ErrorReason;

public class UserAlreadyExistsException extends Throwable {
    public ErrorReason getErrorReason() {
        return new ErrorReason("Такой пользователь уже существует");
    }
}

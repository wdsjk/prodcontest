package ru.prodcontest.exception.user;

import ru.prodcontest.exception.ErrorReason;

public class InvalidPasswordException extends Throwable {
    public ErrorReason getErrorReason() {
        return new ErrorReason("Введен неверный пароль");
    }
}

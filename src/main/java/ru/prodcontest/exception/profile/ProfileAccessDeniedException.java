package ru.prodcontest.exception.profile;

import ru.prodcontest.exception.ErrorReason;

public class ProfileAccessDeniedException extends Throwable {
    public ErrorReason getErrorReason() {
        return new ErrorReason("Профиль не может быть получен");
    }
}

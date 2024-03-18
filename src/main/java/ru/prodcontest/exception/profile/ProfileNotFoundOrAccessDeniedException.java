package ru.prodcontest.exception.profile;

import ru.prodcontest.exception.ErrorReason;

public class ProfileNotFoundOrAccessDeniedException extends Throwable {
    public ErrorReason getErrorReason() {
        return new ErrorReason("Пользователь не найден либо к нему нет доступа");
    }
}

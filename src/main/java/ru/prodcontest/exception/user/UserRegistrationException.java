package ru.prodcontest.exception.user;

import ru.prodcontest.exception.ErrorReason;

public class UserRegistrationException extends Throwable {
    public ErrorReason getErrorReason() {
        return new ErrorReason("Регистрационные данные не соответствуют ожидаемому формату и требованиям");
    }
}

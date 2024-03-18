package ru.prodcontest.exception.post;

import ru.prodcontest.exception.ErrorReason;

public class PostNotFoundException extends Throwable {
    public ErrorReason getErrorReason() {
        return new ErrorReason("Указанный пост не найден либо к нему нет доступа");
    }
}

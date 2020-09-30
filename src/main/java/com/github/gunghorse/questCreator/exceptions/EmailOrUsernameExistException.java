package com.github.gunghorse.questCreator.exceptions;

public class EmailOrUsernameExistException extends RuntimeException {
    public EmailOrUsernameExistException(String message) {
        super(message);
    }
}

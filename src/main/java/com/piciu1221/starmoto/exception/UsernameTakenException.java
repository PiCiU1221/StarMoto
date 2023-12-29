package com.piciu1221.starmoto.exception;

public class UsernameTakenException extends RegistrationException {
    public UsernameTakenException(String message) {
        super(message);
    }
}

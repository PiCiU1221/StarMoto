package com.piciu1221.starmoto.exception;

public class EmailTakenException extends RegistrationException {
    public EmailTakenException(String message) {
        super(message);
    }
}
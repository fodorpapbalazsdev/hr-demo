package com.avinty.hr.exception;

public class EmailAlreadyExistsException extends Throwable {
    public EmailAlreadyExistsException(String email) {
        super("Email: " + email + " already in use");
    }
}

package com.avinty.hr.exception.employee;

public class EmailAlreadyExistsException extends Throwable {
    public EmailAlreadyExistsException(String email) {
        super("Email: " + email + " already in use");
    }
}

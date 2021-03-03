package com.avinty.hr.exception;

public class InvalidEmployeeVMException extends ResourceException {
    public InvalidEmployeeVMException(String type, String message) {
        super(type, message);
    }
}

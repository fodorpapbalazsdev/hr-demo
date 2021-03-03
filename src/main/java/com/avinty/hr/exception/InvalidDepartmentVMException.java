package com.avinty.hr.exception;

public class InvalidDepartmentVMException extends ResourceException {
    public InvalidDepartmentVMException(String type, String message) {
        super(type, message);
    }
}

package com.avinty.hr.exception.employee;

import com.avinty.hr.exception.ResourceException;

public class InvalidEmployeeVMException extends ResourceException {
    public InvalidEmployeeVMException(String type, String message) {
        super(type, message);
    }
}

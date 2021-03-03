package com.avinty.hr.exception.department;

import com.avinty.hr.exception.ResourceException;

public class InvalidDepartmentVMException extends ResourceException {
    public InvalidDepartmentVMException(String type, String message) {
        super(type, message);
    }
}

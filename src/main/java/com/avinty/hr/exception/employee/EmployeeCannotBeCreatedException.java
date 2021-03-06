package com.avinty.hr.exception.employee;

public class EmployeeCannotBeCreatedException extends Exception {
    public EmployeeCannotBeCreatedException(Long id) {
        super("Employee cannot be create because the given department id not exists: " + id);
    }
}

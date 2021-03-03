package com.avinty.hr.exception.employee;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(Long id) {
        super("Employee not found with the given id: " + id);
    }
}

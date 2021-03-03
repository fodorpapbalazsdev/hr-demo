package com.avinty.hr.exception;

public class DepartmentNotFoundException extends Exception {
    public DepartmentNotFoundException(Long id) {
        super("Department not found with the given id: " + id);
    }
}

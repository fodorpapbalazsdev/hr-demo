package com.avinty.hr.exception.department;

public class DepartmentNotFoundException extends Exception {
    public DepartmentNotFoundException(Long id) {
        super("Department not found with the given id: " + id);
    }
}

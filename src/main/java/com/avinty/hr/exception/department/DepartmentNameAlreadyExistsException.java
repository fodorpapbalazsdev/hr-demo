package com.avinty.hr.exception.department;

public class DepartmentNameAlreadyExistsException extends Throwable {
    public DepartmentNameAlreadyExistsException(String name) {
        super("Department with this name: " + name + " already exists in database.");
    }
}

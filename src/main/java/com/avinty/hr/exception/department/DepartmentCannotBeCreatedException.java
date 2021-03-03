package com.avinty.hr.exception.department;

public class DepartmentCannotBeCreatedException extends Exception {
    public DepartmentCannotBeCreatedException(Long managerId) {
        super("Department cannot be create because Employee with id: " + managerId + " not found.");
    }
}

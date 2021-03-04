package com.avinty.hr.service.validator;

import com.avinty.hr.exception.employee.EmployeeErrorConstraints;
import com.avinty.hr.exception.employee.InvalidEmployeeVMException;
import com.avinty.hr.models.EmployeeUpdateVM;
import com.avinty.hr.models.EmployeeVM;
import org.springframework.stereotype.Service;

@Service
public class EmployeeVMValidator implements ResourceValidator<EmployeeVM> {
    public boolean validate(EmployeeVM employeeVM) throws InvalidEmployeeVMException {
        if (employeeVM.getClass().equals(EmployeeUpdateVM.class)) {
            if (((EmployeeUpdateVM) employeeVM).getId() == null) {
                throw new InvalidEmployeeVMException(EmployeeErrorConstraints.ID_NULL, "ID is required for Employee update.");
            }
            if (((EmployeeUpdateVM) employeeVM).getUpdatedBy() == null) {
                throw new InvalidEmployeeVMException(EmployeeErrorConstraints.UPDATED_BY_ID_NULL, "Updated by is required for Employee update.");
            }
        }
        if (!employeeVM.getClass().equals(EmployeeUpdateVM.class)) {
            if (employeeVM.getCreatedBy() == null) {
                throw new InvalidEmployeeVMException(EmployeeErrorConstraints.CREATED_BY_ID_NULL, "Created by id is required for Employee creation.");
            }
        }
        if (employeeVM.getDepartment() == null) {
            throw new InvalidEmployeeVMException(EmployeeErrorConstraints.DEPARTMENT_ID_NULL, "You must specify a department id for Employee creation/update.");
        }
        if (employeeVM.getPassword() == null) {
            throw new InvalidEmployeeVMException(EmployeeErrorConstraints.PASSWORD_NULL, "Password is required for Employee creation/update.");
        }
        if (employeeVM.getEmail() == null) {
            throw new InvalidEmployeeVMException(EmployeeErrorConstraints.EMAIL_NULL, "Email is required for Employee creation/update.");
        }
        return true;
    }
}

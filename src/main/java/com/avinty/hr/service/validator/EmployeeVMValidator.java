package com.avinty.hr.service.validator;

import com.avinty.hr.exception.EmployeeErrorConstraints;
import com.avinty.hr.exception.InvalidEmployeeVMException;
import com.avinty.hr.models.EmployeeVM;
import org.springframework.stereotype.Service;

@Service
public class EmployeeVMValidator implements ResourceValidator<EmployeeVM> {
    public boolean validate(EmployeeVM employeeVM) throws InvalidEmployeeVMException {
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

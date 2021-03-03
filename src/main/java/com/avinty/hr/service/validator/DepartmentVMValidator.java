package com.avinty.hr.service.validator;

import com.avinty.hr.exception.DepartmentErrorConstraints;
import com.avinty.hr.exception.EmployeeErrorConstraints;
import com.avinty.hr.exception.InvalidDepartmentVMException;
import com.avinty.hr.exception.InvalidEmployeeVMException;
import com.avinty.hr.models.DepartmentVM;
import com.avinty.hr.models.EmployeeVM;
import org.springframework.stereotype.Service;

@Service
public class DepartmentVMValidator implements ResourceValidator<DepartmentVM> {
    public boolean validate(DepartmentVM departmentVM) throws InvalidDepartmentVMException {
        if (departmentVM.getName() == null) {
            throw new InvalidDepartmentVMException(DepartmentErrorConstraints.DEPARTMENT_NAME_NULL, "You must specify a department name for Department creation/update.");
        }
        if (departmentVM.getManagerId() == null) {
            throw new InvalidDepartmentVMException(DepartmentErrorConstraints.MANAGER_ID_NULL, "You must specify a manager id for Department creation/update.");
        }
        return true;
    }
}

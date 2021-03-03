package com.avinty.hr.service.validator;

import com.avinty.hr.exception.department.DepartmentErrorConstraints;
import com.avinty.hr.exception.department.InvalidDepartmentVMException;
import com.avinty.hr.models.DepartmentUpdateVM;
import com.avinty.hr.models.DepartmentVM;
import org.springframework.stereotype.Service;

@Service
public class DepartmentVMValidator implements ResourceValidator<DepartmentVM> {
    public boolean validate(DepartmentVM departmentVM) throws InvalidDepartmentVMException {
        if (departmentVM.getClass().equals(DepartmentUpdateVM.class)) {
            if (((DepartmentUpdateVM) departmentVM).getId() == null) {
                throw new InvalidDepartmentVMException(DepartmentErrorConstraints.ID_NULL, "Id is required for Department update.");
            }
        }
        if (departmentVM.getName() == null) {
            throw new InvalidDepartmentVMException(DepartmentErrorConstraints.DEPARTMENT_NAME_NULL, "You must specify a department name for Department creation/update.");
        }
        if (departmentVM.getManagerId() == null) {
            throw new InvalidDepartmentVMException(DepartmentErrorConstraints.MANAGER_ID_NULL, "You must specify a manager id for Department creation/update.");
        }
        return true;
    }
}

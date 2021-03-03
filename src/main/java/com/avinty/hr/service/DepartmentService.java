package com.avinty.hr.service;

import com.avinty.hr.entity.Department;
import com.avinty.hr.exception.department.*;
import com.avinty.hr.models.DepartmentUpdateVM;
import com.avinty.hr.models.DepartmentVM;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartments();

    Department getDepartment(Long id) throws DepartmentNotFoundException;

    Department createDepartment(DepartmentVM Department) throws DepartmentNameAlreadyExistsException, InvalidDepartmentVMException, DepartmentCannotBeCreatedException;

    Department updateDepartment(DepartmentUpdateVM departmentUpdateVM) throws InvalidDepartmentVMException, DepartmentNotFoundException, DepartmentNameAlreadyExistsException, DepartmentCannotBeCreatedException;

    Boolean deleteDepartment(Long id) throws DepartmentNotFoundException, DepartmentCannotBeDeleteException;
}

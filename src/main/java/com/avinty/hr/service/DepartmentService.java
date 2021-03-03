package com.avinty.hr.service;

import com.avinty.hr.entity.Department;
import com.avinty.hr.exception.DepartmentCannotBeCreatedException;
import com.avinty.hr.exception.DepartmentNameAlreadyExistsException;
import com.avinty.hr.exception.DepartmentNotFoundException;
import com.avinty.hr.exception.InvalidDepartmentVMException;
import com.avinty.hr.models.DepartmentVM;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartments();

    Department getDepartment(Long id) throws DepartmentNotFoundException;

    Department createDepartment(DepartmentVM Department) throws DepartmentNameAlreadyExistsException, InvalidDepartmentVMException, DepartmentCannotBeCreatedException;

    Department updateDepartment(Long id);

    Department deleteDepartment(Long id);
}

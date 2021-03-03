package com.avinty.hr.service;

import com.avinty.hr.entity.Department;
import com.avinty.hr.exception.DepartmentNotFoundException;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartments();

    Department getDepartment(Long id) throws DepartmentNotFoundException;

    Department createDepartment(Department Department);

    Department updateDepartment(Long id);

    Department deleteDepartment(Long id);
}

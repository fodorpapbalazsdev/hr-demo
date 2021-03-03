package com.avinty.hr.service;

import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.EmailAlreadyExistsException;
import com.avinty.hr.exception.EmployeeCannotBeCreatedException;
import com.avinty.hr.exception.EmployeeNotFoundException;
import com.avinty.hr.exception.InvalidEmployeeVMException;
import com.avinty.hr.models.EmployeeVM;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();

    Employee getEmployee(Long id) throws EmployeeNotFoundException;

    Employee createEmployee(EmployeeVM employee) throws EmployeeCannotBeCreatedException, EmailAlreadyExistsException, InvalidEmployeeVMException;

    Employee updateEmployee(Long id);

    Employee deleteEmployee(Long id);
}

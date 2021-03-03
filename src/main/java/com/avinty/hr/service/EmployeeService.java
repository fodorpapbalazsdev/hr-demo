package com.avinty.hr.service;

import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.employee.*;
import com.avinty.hr.models.EmployeeUpdateVM;
import com.avinty.hr.models.EmployeeVM;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();

    Employee getEmployee(Long id) throws EmployeeNotFoundException;

    Employee createEmployee(EmployeeVM employee) throws EmployeeCannotBeCreatedException, EmailAlreadyExistsException, InvalidEmployeeVMException;

    Employee updateEmployee(EmployeeUpdateVM employee) throws InvalidEmployeeVMException, EmailAlreadyExistsException, EmployeeCannotBeCreatedException, EmployeeNotFoundException;

    Boolean deleteEmployee(Long id) throws EmployeeNotFoundException, EmployeCannotBeDeleteException;
}

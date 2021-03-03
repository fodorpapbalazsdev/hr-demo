package com.avinty.hr.service;

import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.employee.EmailAlreadyExistsException;
import com.avinty.hr.exception.employee.EmployeeCannotBeCreatedException;
import com.avinty.hr.exception.employee.EmployeeNotFoundException;
import com.avinty.hr.exception.employee.InvalidEmployeeVMException;
import com.avinty.hr.models.EmployeeUpdateVM;
import com.avinty.hr.models.EmployeeVM;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();

    Employee getEmployee(Long id) throws EmployeeNotFoundException;

    Employee createEmployee(EmployeeVM employee) throws EmployeeCannotBeCreatedException, EmailAlreadyExistsException, InvalidEmployeeVMException;

    Employee updateEmployee(EmployeeUpdateVM employee) throws InvalidEmployeeVMException, EmailAlreadyExistsException, EmployeeCannotBeCreatedException, EmployeeNotFoundException;

    Employee deleteEmployee(Long id);
}

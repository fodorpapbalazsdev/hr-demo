package com.avinty.hr.service;

import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.EmployeeNotFoundException;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();

    Employee getEmployee(Long id) throws EmployeeNotFoundException;

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Long id);

    Employee deleteEmployee(Long id);
}

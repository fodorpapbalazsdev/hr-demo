package com.avinty.hr.service;

import com.avinty.hr.entity.Department;
import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.department.DepartmentNotFoundException;
import com.avinty.hr.exception.employee.EmailAlreadyExistsException;
import com.avinty.hr.exception.employee.EmployeeCannotBeCreatedException;
import com.avinty.hr.exception.employee.EmployeeNotFoundException;
import com.avinty.hr.exception.employee.InvalidEmployeeVMException;
import com.avinty.hr.models.EmployeeVM;
import com.avinty.hr.repository.EmployeeRepository;
import com.avinty.hr.service.validator.EmployeeVMValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final EmployeeVMValidator employeeVMValidator;

    @Autowired
    public EmployeeServiceImpl(@Lazy DepartmentService departmentService,
                               EmployeeRepository employeeRepository,
                               EmployeeVMValidator employeeVMValidator) {
        this.departmentService = departmentService;
        this.employeeRepository = employeeRepository;
        this.employeeVMValidator = employeeVMValidator;
    }

    @Override
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    @Override
    public Employee getEmployee(Long id) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (!employeeOptional.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }
        return employeeOptional.get();
    }

    @Override
    public Employee createEmployee(EmployeeVM employeeVm) throws EmployeeCannotBeCreatedException, EmailAlreadyExistsException, InvalidEmployeeVMException {
        employeeVMValidator.validate(employeeVm);
        this.checkIfEmailAlreadyExistsInDatabase(employeeVm.getEmail());
        Department department = this.getDepartmentsFromViewModel(employeeVm);
        return employeeRepository.save(new Employee(employeeVm, department));
    }

    @Override
    public Employee updateEmployee(Long id) {
        return null;
    }

    @Override
    public Employee deleteEmployee(Long id) {
        return null;
    }

    private void checkIfEmailAlreadyExistsInDatabase(String email) throws EmailAlreadyExistsException {
        Optional<Employee> employeesWithSameEmail = this.employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getEmail().equals(email))
                .findAny();
        if (employeesWithSameEmail.isPresent()) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private Department getDepartmentsFromViewModel(EmployeeVM employeeVm) throws EmployeeCannotBeCreatedException {
        try {
            return departmentService.getDepartment(employeeVm.getDepartment());
        } catch (DepartmentNotFoundException e) {
            throw new EmployeeCannotBeCreatedException(employeeVm.getDepartment());
        }
    }
}

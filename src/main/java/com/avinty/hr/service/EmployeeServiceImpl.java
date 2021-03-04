package com.avinty.hr.service;

import com.avinty.hr.entity.Department;
import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.department.DepartmentNotFoundException;
import com.avinty.hr.exception.employee.*;
import com.avinty.hr.models.EmployeeUpdateVM;
import com.avinty.hr.models.EmployeeVM;
import com.avinty.hr.repository.EmployeeRepository;
import com.avinty.hr.service.validator.EmployeeVMValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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
    public Employee updateEmployee(EmployeeUpdateVM employeeUpdateVM) throws InvalidEmployeeVMException, EmailAlreadyExistsException, EmployeeCannotBeCreatedException, EmployeeNotFoundException {
        employeeVMValidator.validate(employeeUpdateVM);
        Employee employeeToUpdate = this.getEmployee(employeeUpdateVM.getId());
        this.checkIfEmailAlreadyExistsInDatabaseExcludeId(employeeUpdateVM.getEmail(), employeeUpdateVM.getId());
        Department department = this.getDepartmentsFromViewModel(employeeUpdateVM);

        employeeToUpdate.setEmail(employeeUpdateVM.getEmail());
        employeeToUpdate.setPassword(employeeUpdateVM.getPassword());
        employeeToUpdate.setFullName(employeeUpdateVM.getFullName());
        employeeToUpdate.setDepartment(department);
        employeeToUpdate.setUpdatedBy(employeeUpdateVM.getUpdatedBy());
        /* updatedAt at logic implemented in database side with trigger */

        return employeeRepository.save(employeeToUpdate);
    }

    @Override
    public Boolean deleteEmployee(Long id) throws EmployeeNotFoundException, EmployeCannotBeDeleteException {
        Employee employeeToDelete = this.getEmployee(id);
        this.checkIfEmployeeIsAManager(employeeToDelete);
        employeeRepository.delete(employeeToDelete);
        return true;
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

    private void checkIfEmailAlreadyExistsInDatabaseExcludeId(String email, Long emloyeeId) throws EmailAlreadyExistsException {
        Optional<Employee> employeesWithSameEmail = this.employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getEmail().equals(email) && employee.getId() != emloyeeId)
                .findAny();
        if (employeesWithSameEmail.isPresent()) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private void checkIfEmployeeIsAManager(Employee employeeToDelete) throws EmployeCannotBeDeleteException {
       List<Department> departmentsManageByEmployee = this.departmentService.getDepartments()
                .stream()
                .filter(dep -> dep.getManager().getId().equals(employeeToDelete.getId()))
                .collect(Collectors.toList());
        if (!departmentsManageByEmployee.isEmpty()) {
            StringJoiner ids = new StringJoiner(", ");
            departmentsManageByEmployee.stream().map(Department::getId).forEach(id -> ids.add(id.toString()));
            throw new EmployeCannotBeDeleteException("Employee cannot be delete, because she/he is managing the following Departments with id: " + ids.toString());
        }
    }
}

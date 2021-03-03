package com.avinty.hr.service;

import com.avinty.hr.entity.Department;
import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.*;
import com.avinty.hr.models.DepartmentVM;
import com.avinty.hr.repository.DepartmentRepository;
import com.avinty.hr.service.validator.DepartmentVMValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentVMValidator departmentVMValidator;
    private final EmployeeService employeeService;

    @Autowired
    public DepartmentServiceImpl(@Lazy EmployeeService employeeService,
                                 DepartmentRepository departmentRepository,
                                 DepartmentVMValidator departmentVMValidator) {
        this.employeeService = employeeService;
        this.departmentRepository = departmentRepository;
        this.departmentVMValidator = departmentVMValidator;
    }

    @Override
    public List<Department> getDepartments() {
        List<Department> Departments = departmentRepository.findAll();
        return Departments;
    }

    @Override
    public Department getDepartment(Long id) throws DepartmentNotFoundException {
        Optional<Department> DepartmentOptional = departmentRepository.findById(id);
        if (!DepartmentOptional.isPresent()) {
            throw new DepartmentNotFoundException(id);
        }
        return DepartmentOptional.get();
    }

    @Override
    public Department createDepartment(DepartmentVM departmentVm) throws DepartmentNameAlreadyExistsException, InvalidDepartmentVMException, DepartmentCannotBeCreatedException {
        departmentVMValidator.validate(departmentVm);
        this.checkIfDepartmentNameAlreadyExistsInDatabase(departmentVm.getName());
        Employee manager = this.getManagerEmployeeFromVM(departmentVm);
        return departmentRepository.save(new Department(departmentVm, manager));
    }

    @Override
    public Department updateDepartment(Long id) {
        return null;
    }

    @Override
    public Department deleteDepartment(Long id) {
        return null;
    }

    private void checkIfDepartmentNameAlreadyExistsInDatabase(String name) throws DepartmentNameAlreadyExistsException {
        Optional<Department> departmentsWithSameName = this.departmentRepository.findAll()
                .stream()
                .filter(department -> department.getName().equals(name))
                .findAny();
        if (departmentsWithSameName.isPresent()) {
            throw new DepartmentNameAlreadyExistsException(name);
        }
    }

    private Employee getManagerEmployeeFromVM(DepartmentVM departmentVm) throws DepartmentCannotBeCreatedException {
        try {
            return employeeService.getEmployee(departmentVm.getManagerId());
        } catch (EmployeeNotFoundException e) {
            throw new DepartmentCannotBeCreatedException(departmentVm.getManagerId());
        }
    }

}

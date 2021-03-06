package com.avinty.hr.service;

import com.avinty.hr.entity.Department;
import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.department.*;
import com.avinty.hr.exception.employee.EmployeeNotFoundException;
import com.avinty.hr.models.DepartmentUpdateVM;
import com.avinty.hr.models.DepartmentVM;
import com.avinty.hr.repository.DepartmentRepository;
import com.avinty.hr.service.validator.DepartmentVMValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartment(Long id) throws DepartmentNotFoundException {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (!departmentOptional.isPresent()) {
            throw new DepartmentNotFoundException(id);
        }
        return departmentOptional.get();
    }

    @Override
    public Department createDepartment(DepartmentVM departmentVm) throws DepartmentNameAlreadyExistsException, InvalidDepartmentVMException, DepartmentCannotBeCreatedException {
        departmentVMValidator.validate(departmentVm);
        this.checkIfDepartmentNameAlreadyExistsInDatabase(departmentVm.getName());
        Employee manager = this.getManagerEmployeeFromVM(departmentVm);
        return departmentRepository.save(new Department(departmentVm, manager));
    }

    @Override
    public Department updateDepartment(DepartmentUpdateVM departmentUpdateVM) throws InvalidDepartmentVMException, DepartmentNotFoundException, DepartmentNameAlreadyExistsException, DepartmentCannotBeCreatedException {
        departmentVMValidator.validate(departmentUpdateVM);
        Department departmentToUpdate = this.getDepartment(departmentUpdateVM.getId());
        this.checkIfDepartmentNameAlreadyExistsInDatabaseExclude(departmentUpdateVM.getName(), departmentUpdateVM.getId());
        Employee manager = this.getManagerEmployeeFromVM(departmentUpdateVM);

        departmentToUpdate.setName(departmentUpdateVM.getName());
        departmentToUpdate.setManager(manager);
        departmentToUpdate.setUpdatedBy(departmentUpdateVM.getUpdatedBy());
        /* updatedAt at logic implemented in database side with trigger */

        return departmentRepository.save(departmentToUpdate);
    }

    @Override
    public Boolean deleteDepartment(Long id) throws DepartmentNotFoundException, DepartmentCannotBeDeleteException {
        Department departmentToDelete = this.getDepartment(id);
        this.checkIfDepartmentDeletable(departmentToDelete);
        departmentRepository.delete(departmentToDelete);
        return true;
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

    private void checkIfDepartmentNameAlreadyExistsInDatabaseExclude(String name, Long id) throws DepartmentNameAlreadyExistsException {
        List<Department> departmentsWithSameName = this.departmentRepository.findAll()
                .stream()
                .filter(department -> department.getName().equals(name) && department.getId() != id)
                .collect(Collectors.toList());
        if (!departmentsWithSameName.isEmpty()) {
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

    private void checkIfDepartmentDeletable(Department departmentToDelete) throws DepartmentCannotBeDeleteException {
        if (!departmentToDelete.getEmployees().isEmpty()) {
            throw new DepartmentCannotBeDeleteException("Department cannot be delete, because it's employee list is not empty!");
        }
    }

}

package com.avinty.hr.service;

import com.avinty.hr.entity.Department;
import com.avinty.hr.exception.DepartmentNotFoundException;
import com.avinty.hr.repository.DepartmentRepository;
import com.avinty.hr.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

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
    public Department createDepartment(Department Department) {
        return null;
    }

    @Override
    public Department updateDepartment(Long id) {
        return null;
    }

    @Override
    public Department deleteDepartment(Long id) {
        return null;
    }
}

package com.avinty.hr.service;

import com.avinty.hr.entity.Department;
import com.avinty.hr.exception.department.DepartmentNotFoundException;
import com.avinty.hr.repository.DepartmentRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DepartmentServiceImplTest Test")
class DepartmentServiceImplTest {

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Test
    void getDepartments() {
        List<Department> dummyDepartments = Arrays.asList(new Department(), new Department(), new Department());
        when(departmentRepository.findAll()).thenReturn(dummyDepartments);
        List<Department> result = departmentService.getDepartments();
        assertEquals(3, result.size());
    }

    @SneakyThrows
    @Test
    void getDepartmentShouldBeNotNull() {
        Department dummyDepartment = new Department();
        Optional<Department> dummyDepartmentOpt = Optional.of(dummyDepartment);
        when(departmentRepository.findById(1L)).thenReturn(dummyDepartmentOpt);
        Department result = departmentService.getDepartment(1L);
        assertNotNull(result);
    }

    @SneakyThrows
    @Test
    void getDepartmentShoulThrowDepartmentNotFoundException() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartment(1L));
    }
}

package com.avinty.hr.service;

import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.employee.EmployeeNotFoundException;
import com.avinty.hr.repository.EmployeeRepository;
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
@DisplayName("EmployeeServiceImpl Test")
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Test
    void getEmployees() {
        List<Employee> dummyEmployees = Arrays.asList(new Employee(), new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(dummyEmployees);
        List<Employee> result = employeeService.getEmployees();
        assertEquals(3, result.size());
    }

    @SneakyThrows
    @Test
    void getEmployeeShouldBeNotNull() {
        Employee dummyEmployee = new Employee();
        Optional<Employee> dummyEmployeeOpt = Optional.of(dummyEmployee);
        when(employeeRepository.findById(1L)).thenReturn(dummyEmployeeOpt);
        Employee result = employeeService.getEmployee(1L);
        assertNotNull(result);
    }

    @SneakyThrows
    @Test
    void getEmployeeShoulThrowEmployeeNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(1L));
    }
}

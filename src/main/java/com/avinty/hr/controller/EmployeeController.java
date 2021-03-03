package com.avinty.hr.controller;


import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.employee.*;
import com.avinty.hr.models.EmployeeUpdateVM;
import com.avinty.hr.models.EmployeeVM;
import com.avinty.hr.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/API/V1/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getEmployees(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeVM employee) {
        try {
            return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.OK);
        } catch (EmployeeCannotBeCreatedException | EmailAlreadyExistsException | InvalidEmployeeVMException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeUpdateVM employee) {
        try {
            return new ResponseEntity<>(employeeService.updateEmployee(employee), HttpStatus.OK);
        } catch (InvalidEmployeeVMException | EmailAlreadyExistsException | EmployeeNotFoundException | EmployeeCannotBeCreatedException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
        } catch (EmployeeNotFoundException | EmployeCannotBeDeleteException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}

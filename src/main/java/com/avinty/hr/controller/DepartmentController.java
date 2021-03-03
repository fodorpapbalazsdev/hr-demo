package com.avinty.hr.controller;


import com.avinty.hr.entity.Department;
import com.avinty.hr.exception.department.*;
import com.avinty.hr.models.DepartmentUpdateVM;
import com.avinty.hr.models.DepartmentVM;
import com.avinty.hr.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/API/V1/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Department>> getAllDepartments() {
        return new ResponseEntity<>(departmentService.getDepartments(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(departmentService.getDepartment(id), HttpStatus.OK);
        } catch (DepartmentNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentVM department) {
        try {
            return new ResponseEntity<>(departmentService.createDepartment(department), HttpStatus.OK);
        } catch (DepartmentCannotBeCreatedException | InvalidDepartmentVMException | DepartmentNameAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Department> updateDepartment(@RequestBody DepartmentUpdateVM departmentUpdateVM) {
        try {
            return new ResponseEntity<>(departmentService.updateDepartment(departmentUpdateVM), HttpStatus.OK);
        } catch (InvalidDepartmentVMException | DepartmentNotFoundException | DepartmentNameAlreadyExistsException | DepartmentCannotBeCreatedException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(departmentService.deleteDepartment(id), HttpStatus.OK);
        } catch (DepartmentNotFoundException | DepartmentCannotBeDeleteException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}

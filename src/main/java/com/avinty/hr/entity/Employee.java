package com.avinty.hr.entity;

import com.avinty.hr.models.EmployeeVM;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@Getter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @JoinColumn(name = "full_name")
    private String fullName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dep_id", referencedColumnName = "id")
    private Department department;

    public Employee(EmployeeVM employeeVm, Department department) {
        this.email = employeeVm.getEmail();
        this.password = employeeVm.getPassword();
        this.fullName = employeeVm.getFullName();
        this.department = department;
    }
}

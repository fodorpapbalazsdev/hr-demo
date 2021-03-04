package com.avinty.hr.entity;

import com.avinty.hr.models.DepartmentVM;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Employee manager;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<Employee> employees;

    @JoinColumn(name = "created_at")
    private Date createdAt;

    @JoinColumn(name = "created_by")
    private Long createdBy;

    @JoinColumn(name = "updated_at")
    private Date updatedAt;

    @JoinColumn(name = "updated_by")
    private Long updatedBy;

    public Department(DepartmentVM departmentVm, Employee manager) {
        this.name = departmentVm.getName();
        this.manager = manager;
        this.employees = new HashSet<>();
        this.createdBy = departmentVm.getCreatedBy();
        this.updatedBy = this.createdBy;
        /* createdAt at logic implemented in database side with trigger */
    }
}

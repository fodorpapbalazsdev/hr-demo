package com.avinty.hr.entity;

import com.avinty.hr.models.EmployeeVM;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "dep_id", referencedColumnName = "id")
    private Department department;

    @JoinColumn(name = "created_at")
    private Date createdAt;

    @JoinColumn(name = "created_by")
    private Long createdBy;

    @JoinColumn(name = "updated_at")
    private Date updatedAt;

    @JoinColumn(name = "updated_by")
    private Long updatedBy;

    @JoinColumn(name = "profile_picture")
    private String profilePicture;

    public Employee(EmployeeVM employeeVm, Department department) {
        this.email = employeeVm.getEmail();
        this.password = employeeVm.getPassword();
        this.fullName = employeeVm.getFullName();
        this.department = department;
        this.createdBy = employeeVm.getCreatedBy();
        this.updatedBy = this.createdBy;
        this.profilePicture = employeeVm.getProfilePicture();
        /* createdAt at logic implemented in database side with trigger */
    }
}

package com.avinty.hr.models;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class EmployeeVM {
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String fullName;
    @NonNull
    private Long department;
    @NonNull
    private Long createdBy;
}

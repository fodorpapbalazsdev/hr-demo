package com.avinty.hr.models;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class EmployeeUpdateVM extends EmployeeVM {
    @NonNull
    private Long id;
}

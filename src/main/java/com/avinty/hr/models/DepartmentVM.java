package com.avinty.hr.models;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class DepartmentVM {
    @NonNull
    private String name;
    @NonNull
    private Long managerId;
}

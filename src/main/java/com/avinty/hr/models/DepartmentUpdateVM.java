package com.avinty.hr.models;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class DepartmentUpdateVM extends DepartmentVM {
    @NonNull
    private Long id;
}

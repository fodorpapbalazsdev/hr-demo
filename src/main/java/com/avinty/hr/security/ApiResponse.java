package com.avinty.hr.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private final Boolean success;
    private final String message;

}

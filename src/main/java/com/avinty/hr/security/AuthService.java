package com.avinty.hr.security;

import com.avinty.hr.exception.ResourceException;
import com.avinty.hr.exception.employee.EmployeeNotFoundException;
import com.avinty.hr.models.LoginRequest;

public interface AuthService {
    JwtAuthenticationResponse getJwtAuthentication(LoginRequest loginRequest) throws ResourceException, EmployeeNotFoundException;

    ApiResponse validateToken(ValidateTokenRequest validateTokenRequest) throws ResourceException, EmployeeNotFoundException;
}

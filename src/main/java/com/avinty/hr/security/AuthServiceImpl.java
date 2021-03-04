package com.avinty.hr.security;

import com.avinty.hr.entity.Employee;
import com.avinty.hr.exception.ResourceException;
import com.avinty.hr.exception.employee.EmployeeNotFoundException;
import com.avinty.hr.models.LoginRequest;
import com.avinty.hr.security.jwt.JwtTokenProvider;
import com.avinty.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    EmployeeService employeeService;

    @Override
    public JwtAuthenticationResponse getJwtAuthentication(LoginRequest loginRequest) throws ResourceException, EmployeeNotFoundException {

        // TODO: Validation goes here
        if (loginRequest.getPassword() == null || loginRequest.getEmail() == null) {
            throw new ResourceException("emial-or-password-null", "Email and Password is required for token generation!");
        }

        String jwt = jwtTokenProvider.generateToken(loginRequest);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);
        Employee employee = employeeService.getEmployeeByEmail(loginRequest.getEmail());

        // TODO: it should coming from database also
        if (employee.getEmail().equals("admin@admin.com")) {
            jwtAuthenticationResponse.setRole("ROLE_ADMIN");
        } else {
            jwtAuthenticationResponse.setRole("ROLE_USER");
        }

        return jwtAuthenticationResponse;
    }

    @Override
    public ApiResponse validateToken(ValidateTokenRequest validateTokenRequest) throws ResourceException, EmployeeNotFoundException {
        if (validateTokenRequest.getToken() == null) {
            throw new ResourceException("token-null", "Token is required for token validation!");
        }

        String jwt = validateTokenRequest.getToken();
        if (jwtTokenProvider.validateToken(jwt)) {
            String email = jwtTokenProvider.getEmailFromJwt(jwt);
            employeeService.getEmployeeByEmail(email);
            return new ApiResponse(Boolean.TRUE, "Valid token for email " + email);
        }
        return new ApiResponse(Boolean.FALSE, "Invalid token");
    }
}

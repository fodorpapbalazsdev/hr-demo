package com.avinty.hr.controller;

import com.avinty.hr.exception.ResourceException;
import com.avinty.hr.exception.employee.EmailAlreadyExistsException;
import com.avinty.hr.exception.employee.EmployeeNotFoundException;
import com.avinty.hr.models.LoginRequest;
import com.avinty.hr.security.ApiResponse;
import com.avinty.hr.security.AuthService;
import com.avinty.hr.security.JwtAuthenticationResponse;
import com.avinty.hr.security.ValidateTokenRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/generatetoken")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.getJwtAuthentication(loginRequest));
        } catch (ResourceException | EmployeeNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("/validatetoken")
    public ResponseEntity<ApiResponse> authenticateUser(@RequestBody ValidateTokenRequest validateTokenRequest) {
        try {
            return ResponseEntity.ok(authService.validateToken(validateTokenRequest));
        } catch (ResourceException | EmployeeNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}

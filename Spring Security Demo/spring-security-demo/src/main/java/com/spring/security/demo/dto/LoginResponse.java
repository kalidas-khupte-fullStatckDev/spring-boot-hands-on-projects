package com.spring.security.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginResponse {
    private String jwtTokenValue;
    private Integer statusCode;
    private String userName;
    private List<String> roles;
    private boolean isAuthenticated;
}

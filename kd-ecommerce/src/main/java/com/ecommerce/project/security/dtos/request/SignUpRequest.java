package com.ecommerce.project.security.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    private Set<String> roles;
}

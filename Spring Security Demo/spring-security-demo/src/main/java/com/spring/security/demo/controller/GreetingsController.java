package com.spring.security.demo.controller;

import com.spring.security.demo.dto.LoginRequest;
import com.spring.security.demo.dto.LoginResponse;
import com.spring.security.demo.jwt.AuthTokenFilter;
import com.spring.security.demo.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class GreetingsController {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(GreetingsController.class);

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String helloUser() {
        return "Hello User";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                    loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            logger.error("AuthenticationException : {}", e.getMessage());
            Map<String, Object> errResp = new HashMap<>();
            errResp.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            errResp.put("errorMsg", "Bad Credentials");
            return new ResponseEntity<>(errResp, HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateJWTToken(userDetails);
        LoginResponse response = new LoginResponse();
        response.setJwtTokenValue(jwtToken);
        response.setUserName(userDetails.getUsername());
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        response.setRoles(roles);
        response.setStatusCode(HttpServletResponse.SC_ACCEPTED);
        response.setAuthenticated(true);
        return ResponseEntity.ok(response);
    }
}

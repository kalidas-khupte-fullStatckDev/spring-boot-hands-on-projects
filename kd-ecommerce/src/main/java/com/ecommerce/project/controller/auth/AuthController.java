package com.ecommerce.project.controller.auth;

import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.model.enums.AppRole;
import com.ecommerce.project.repository.RoleRepository;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.security.dtos.request.LoginRequest;
import com.ecommerce.project.security.dtos.request.SignUpRequest;
import com.ecommerce.project.security.dtos.response.MessageResponse;
import com.ecommerce.project.security.dtos.response.UserInfoResponse;
import com.ecommerce.project.security.jwt.JWTUtils;
import com.ecommerce.project.security.jwt.services.UserDetailsImpl;
import com.ecommerce.project.security.jwt.services.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("signin")
    public ResponseEntity<?> userLogin(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                    loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            log.error("AuthenticationException : {}", e.getMessage());
            Map<String, Object> errResp = new HashMap<>();
            errResp.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            errResp.put("errorMsg", "Bad Credentials");
            return new ResponseEntity<>(errResp, HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        assert userDetails != null;
        ResponseCookie jwtCookie = jwtUtils.generateJWTCookies(userDetails);
        UserInfoResponse response = new UserInfoResponse();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        response.setId(userDetails.getId());
        response.setUserName(userDetails.getUsername());
        response.setRoles(roles);
        response.setStatusCode(HttpServletResponse.SC_ACCEPTED);
        response.setAuthenticated(true);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }

    @GetMapping("sign-out")
    public ResponseEntity<?> signOut(Authentication authentication) {
        //authentication.setAuthenticated(false);
        ResponseCookie clearedCookie = jwtUtils.getCleanJWTCookies();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, clearedCookie.toString()).body(new MessageResponse("User has been logged out"));
    }

    @PostMapping("signup")
    public ResponseEntity<?> userRegistration(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUserName())) {
            String msg = "User is already exists with userName:" + signUpRequest.getUserName();
            return new ResponseEntity<>(new MessageResponse(msg), HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            String msg = "User is already exists with Email:" + signUpRequest.getEmail();
            return new ResponseEntity<>(new MessageResponse(msg), HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        User user = new User(
                signUpRequest.getUserName(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword())
        );

        Set<Role> jpaRoles = new HashSet<>();

        if (signUpRequest.getRoles() == null) {
            // Role userRole = roleRepository.findByRoleNames(AppRole.USER_ROLE).orElseThrow(() -> new MessageResponse(""))
            Role userRole = roleRepository.findByRoleName(AppRole.USER_ROLE).orElseThrow(() -> new RuntimeException("ERROR : Role is not present"));
            jpaRoles.add(userRole);
        } else {
            signUpRequest.getRoles().forEach(s -> {
                switch (s) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByRoleName(AppRole.ADMIN_ROLE).orElseThrow(() -> new RuntimeException("ERROR : Role is not present"));
                        jpaRoles.add(adminRole);
                    }

                    case "seller" -> {
                        Role sellerRole = roleRepository.findByRoleName(AppRole.SELLER_ROLE).orElseThrow(() -> new RuntimeException("ERROR : Role is not present"));
                        jpaRoles.add(sellerRole);
                    }

                    default -> {
                        Role userRole = roleRepository.findByRoleName(AppRole.USER_ROLE).orElseThrow(() -> new RuntimeException("ERROR : Role is not present"));
                        jpaRoles.add(userRole);
                    }
                }
            });
        }
        user.setUserRole(jpaRoles);
        userRepository.save(user);
        return new ResponseEntity<>(new MessageResponse("New User Registration is successful!!"), HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }

    @GetMapping("getLoggedInUserName")
    public ResponseEntity<?> getCurrentLoggedInUserName(Authentication authentication) {
        if (authentication != null)
            return ResponseEntity.ok(authentication.getName());
        else
            return ResponseEntity.ok(new MessageResponse("No current logged in User"));
    }

    @GetMapping("getLoggedInUserDetails")
    public ResponseEntity<?> getCurrentLoggedInUserDetails(Authentication authentication) {
        if (authentication != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            assert userDetails != null;
            UserInfoResponse response = new UserInfoResponse();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            response.setId(userDetails.getId());
            response.setUserName(userDetails.getUsername());
            response.setRoles(roles);
            response.setStatusCode(HttpServletResponse.SC_ACCEPTED);
            response.setAuthenticated(true);
            return ResponseEntity.ok().body(response);
        } else
            return ResponseEntity.ok(new MessageResponse("No current logged in User"));
    }
}



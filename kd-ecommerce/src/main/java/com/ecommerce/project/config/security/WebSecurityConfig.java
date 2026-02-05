package com.ecommerce.project.config.security;

import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.model.enums.AppRole;
import com.ecommerce.project.repository.RoleRepository;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.security.jwt.AuthEntryPointJwt;
import com.ecommerce.project.security.jwt.AuthTokenFilter;
import com.ecommerce.project.security.jwt.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;


/**
 * This is Custom Security Filter configuration for web security. It relies on Spring Security's
 * content-negotiation strategy to determine what sort of authentication to use.
 */

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    AuthEntryPointJwt authEntryPointJwt;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authConfig) {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authEntryPointJwt);
        });
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/v3/api-docs").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
               // .requestMatchers("/api/public/**").permitAll()
              //  .requestMatchers("/api/admin/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/images/**").permitAll()
                .anyRequest().authenticated());

        http.authenticationProvider(getDaoAuthenticationProvider());
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    WebSecurityCustomizer getWebSecurityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
                    "/swagger-ui.html", "/webjars/**");
        };
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByRoleName(AppRole.ADMIN_ROLE).orElseGet(() -> {
                Role role = new Role();
                role.setRoleName(AppRole.ADMIN_ROLE);
                return roleRepository.save(role);
            });

            Role sellerRole = roleRepository.findByRoleName(AppRole.SELLER_ROLE).orElseGet(() -> {
                Role role = new Role();
                role.setRoleName(AppRole.SELLER_ROLE);
                return roleRepository.save(role);
            });

            Role userRole = roleRepository.findByRoleName(AppRole.USER_ROLE).orElseGet(() -> {
                Role role = new Role();
                role.setRoleName(AppRole.USER_ROLE);
                return roleRepository.save(role);
            });

           Set<Role> userDefaultRoleSet = Set.of(userRole);
           Set<Role> userSellerRoleSet = Set.of(sellerRole);
           Set<Role> userAdminRoleSet = Set.of(userRole, sellerRole, adminRole);

            User normaluser = userRepository.findUserByUsername("user").orElseGet(() -> {
                User user = new User();
                user.setUsername("user");
                user.setEmail("user@email.com");
                user.setPassword(passwordEncoder.encode("password"));
                return userRepository.save(user);
            });

            User sellerUserOut = userRepository.findUserByUsername("seller").orElseGet(() -> {
                User sellerUserIn = new User();
                sellerUserIn.setUsername("seller");
                sellerUserIn.setEmail("seller@email.com");
                sellerUserIn.setPassword( passwordEncoder.encode("seller"));
                return userRepository.save(sellerUserIn);
            });

            User adminUserOut = userRepository.findUserByUsername("admin").orElseGet(() -> {
                User adminUserIn = new User();
                adminUserIn.setUsername("admin");
                adminUserIn.setEmail("admin@email.com");
                adminUserIn.setPassword( passwordEncoder.encode("admin"));
                return userRepository.save(adminUserIn);
            });

            normaluser.setUserRole(userDefaultRoleSet);
            userRepository.save(normaluser);

            sellerUserOut.setUserRole(userSellerRoleSet);
            userRepository.save(sellerUserOut);

            adminUserOut.setUserRole(userAdminRoleSet);
            userRepository.save(adminUserOut);


        };
    }

}

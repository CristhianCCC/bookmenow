package com.businessdomain.user.controller;

import com.businessdomain.user.dto.AuthRequest;
import com.businessdomain.user.dto.AuthResponse;
import com.businessdomain.user.exceptions.exceptions.BusinessRuleException;
import com.businessdomain.user.exceptions.validations.UserValidations;
import com.businessdomain.user.service.JwtService;
import com.businessdomain.user.service.impl.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param request the user's credentials
     * @return the JWT token if authentication is successful
     */
    @Operation(summary = "Authenticate user", description = "Authenticate a user using email and password and receive a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            // exception injection from validations
            UserValidations.validateCredentials(request);
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

            var roles = userDetails.getAuthorities()
                    .stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());

            String token = jwtService.generateToken(
                    Map.of("role", roles),
                    userDetails.getUsername()
            );

            return new AuthResponse(token);

        } catch (BusinessRuleException ex) {
            throw ex;
        }
    }

}

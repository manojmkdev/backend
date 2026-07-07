package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SystemUser;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(JwtService jwtService,AuthenticationManager authenticationManager,AuthService authService){
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SystemUser user) {
        try {
            SystemUser saved = authService.register(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            // Duplicate email causes DataIntegrityViolationException
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {
                error.put("message", "Email is already registered. Please use a different email.");
            } else {
                error.put("message", "Registration failed: " + e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }    

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody SystemUser user) {
      try {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                ));

        String token = jwtService.generateToken(user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login Successful");
        response.put("token", token);

        return ResponseEntity.ok(response);

    } catch (BadCredentialsException e) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Invalid Email or Password");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
}
   
}

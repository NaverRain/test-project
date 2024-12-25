package com.naverrain.test.controllers;

import com.naverrain.test.dtos.RegistrationUserDto;
import com.naverrain.test.dtos.UserDto;
import com.naverrain.test.exceptions.InvalidLoginException;
import com.naverrain.test.exceptions.InvalidPasswordException;
import com.naverrain.test.service.UserService;
import com.naverrain.test.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            UserDto registeredUserDto = userService.registerUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserDto);
        } catch (InvalidLoginException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegistrationUserDto loginRequest) {
        try {
            UserDto userDto = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            String token = jwtUtil.generateToken(userDto.getUsername());
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }

}

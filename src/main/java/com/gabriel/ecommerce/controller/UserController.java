package com.gabriel.ecommerce.controller;

import com.gabriel.ecommerce.dto.UserRegisterDto;
import com.gabriel.ecommerce.dto.UserResponseDto;
import com.gabriel.ecommerce.entity.User;
import com.gabriel.ecommerce.service.UserService;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        return ResponseEntity.ok(authentication.getPrincipal());
    }
}

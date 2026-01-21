package com.gabriel.ecommerce.controller;

import com.gabriel.ecommerce.dto.UserRegisterDto;
import com.gabriel.ecommerce.dto.UserResponseDto;
import com.gabriel.ecommerce.entity.User;
import com.gabriel.ecommerce.service.UserService;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

}

package com.gabriel.ecommerce.controller;

import com.gabriel.ecommerce.dto.LoginRequestDto;
import com.gabriel.ecommerce.dto.UserRegisterDto;
import com.gabriel.ecommerce.dto.UserResponseDto;
import com.gabriel.ecommerce.entity.User;
import com.gabriel.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRegisterDto dto) {
        User user = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.email(),
                        requestDto.password()
                )
        );

        return ResponseEntity.ok("Login realizado com sucesso");
    }
}

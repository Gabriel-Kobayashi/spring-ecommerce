package com.gabriel.ecommerce.controller;

import com.gabriel.ecommerce.dto.LoginRequestDto;
import com.gabriel.ecommerce.dto.LoginResponseDto;
import com.gabriel.ecommerce.dto.UserRegisterDto;
import com.gabriel.ecommerce.dto.UserResponseDto;
import com.gabriel.ecommerce.entity.User;
import com.gabriel.ecommerce.security.JwtService;
import com.gabriel.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(UserService service, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
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
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.email(),
                        requestDto.password()
                )
        );

        UserDetails user = userDetailsService
                .loadUserByUsername(requestDto.email());

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}

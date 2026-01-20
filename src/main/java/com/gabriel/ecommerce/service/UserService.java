package com.gabriel.ecommerce.service;

import com.gabriel.ecommerce.dto.UserRegisterDto;
import com.gabriel.ecommerce.dto.UserResponseDto;
import com.gabriel.ecommerce.entity.User;
import com.gabriel.ecommerce.entity.enums.Role;
import com.gabriel.ecommerce.exception.EmailAlreadyExistsException;
import com.gabriel.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UserRegisterDto dto) {

        if (repository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("Email já cadastrado");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.USER);

        return repository.save(user);
    }

}

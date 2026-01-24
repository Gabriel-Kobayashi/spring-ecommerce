package com.gabriel.ecommerce.controller;

import com.gabriel.ecommerce.dto.product.ProductRequestDto;
import com.gabriel.ecommerce.dto.product.ProductResponseDto;
import com.gabriel.ecommerce.entity.Product;
import com.gabriel.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        List<ProductResponseDto> products = service.findAll().stream().map(ProductResponseDto::fromEntity).toList();
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id) {
        Product product = service.findById(id);
        return ResponseEntity.ok(ProductResponseDto.fromEntity(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductRequestDto dto) {
        Product savedProduct = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponseDto.fromEntity(savedProduct));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable Long id, @RequestBody @Valid ProductRequestDto dto) {
        Product updatedProduct = service.update(id, dto);
        return ResponseEntity.ok(ProductResponseDto.fromEntity(updatedProduct));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.gabriel.ecommerce.service;

import com.gabriel.ecommerce.dto.product.ProductRequestDto;
import com.gabriel.ecommerce.entity.Product;
import com.gabriel.ecommerce.exception.ProductNotFoundException;
import com.gabriel.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));
    }

    public Product save(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());

        return repository.save(product);
    }

    public Product update(Long id, ProductRequestDto dto) {
        Product existing = findById(id);

        existing.setName(dto.name());
        existing.setDescription(dto.description());
        existing.setPrice(dto.price());
        existing.setStock(dto.stock());

        return repository.save(existing);
    }

    public void delete(Long id) {
        Product product = findById(id);
        repository.delete(product);
    }
}

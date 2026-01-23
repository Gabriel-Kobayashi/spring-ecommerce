package com.gabriel.ecommerce.service;

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

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product update(Long id, Product product) {
        Product existing = findById(id);

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());

        return repository.save(existing);
    }

    public void delete(Long id) {
        Product product = findById(id);
        repository.delete(product);
    }
}

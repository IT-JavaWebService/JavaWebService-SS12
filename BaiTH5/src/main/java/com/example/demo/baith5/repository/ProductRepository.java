package com.example.demo.baith5.repository;


import com.example.demo.baith5.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final List<Product> productList = new ArrayList<>();
    private Long autoId = 1L;

    public ProductRepository() {
        productList.add(new Product(autoId++, "Mì Tôm", 5000.0, 100));
    }

    public List<Product> findAll() { return productList; }

    public Optional<Product> findById(Long id) {
        return productList.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(autoId++);
            productList.add(product);
        }
        return product;
    }

    public boolean deleteById(Long id) {
        return productList.removeIf(p -> p.getId().equals(id));
    }
}
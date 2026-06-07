package com.example.demo.baith5.service;

import com.example.demo.baith5.model.Product;
import com.example.demo.baith5.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product(1L, "Bánh Quy", 20000.0, 50);
    }

    @Test
    void getAll_WhenDataExists_ReturnNonEmptyList() {
        List<Product> list = new ArrayList<>();
        list.add(sampleProduct);
        when(productRepository.findAll()).thenReturn(list);

        List<Product> result = productService.getAllProducts();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getById_WhenFound_ReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        Product result = productService.getProductById(1L);
        assertNotNull(result);
        assertEquals("Bánh Quy", result.getName());
    }

    @Test
    void getById_WhenNotFound_ThrowRuntimeException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById(99L));
    }

    @Test
    void addProduct_Success_ReturnProductWithId() {
        Product input = new Product(null, "Kẹo Mút", 2000.0, 200);
        Product saved = new Product(2L, "Kẹo Mút", 2000.0, 200);
        when(productRepository.save(any(Product.class))).thenReturn(saved);

        Product result = productService.addProduct(input);
        assertNotNull(result.getId());
        assertEquals(2L, result.getId());
    }

    @Test
    void deleteProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.deleteById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).deleteById(1L);
    }
}
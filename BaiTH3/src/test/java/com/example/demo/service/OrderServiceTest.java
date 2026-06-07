package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
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
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = new Order(1L, "Nguyen Van A", "Laptop Dell", 1, 1500.0);
    }

    @Test
    void getAllOrders_ReturnNonEmptyList() {
        // Given
        List<Order> list = new ArrayList<>();
        list.add(sampleOrder);
        when(orderRepository.findAll()).thenReturn(list);

        // When
        List<Order> result = orderService.getAllOrders();

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getOrderById_Found() {
        // Given
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));

        // When
        Order result = orderService.getOrderById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Nguyen Van A", result.getCustomerName());
    }

    @Test
    void getOrderById_NotFound_ThrowException() {
        // Given
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(99L);
        });
        assertEquals("Order not found with id: 99", exception.getMessage());
    }

    @Test
    void addOrder_Success() {
        // Given
        Order newOrder = new Order(null, "Tran Thi B", "iPhone 15", 2, 2400.0);
        Order savedOrder = new Order(2L, "Tran Thi B", "iPhone 15", 2, 2400.0);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // When
        Order result = orderService.addOrder(newOrder);

        // Then
        assertNotNull(result.getId());
        assertEquals(2L, result.getId());
        assertEquals("Tran Thi B", result.getCustomerName());
    }

    @Test
    void updateOrder_Success() {
        // Given
        Order updatedInfo = new Order(null, "Nguyen Van A Cập Nhật", "Laptop Dell Pro", 1, 1800.0);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(sampleOrder);

        // When
        Order result = orderService.updateOrder(1L, updatedInfo);

        // Then
        assertEquals("Nguyen Van A Cập Nhật", result.getCustomerName());
        assertEquals("Laptop Dell Pro", result.getProduct());
    }

    @Test
    void deleteOrder_RemovesElement() {
        // Given
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(orderRepository.deleteById(1L)).thenReturn(true);

        // When & Then (Đảm bảo không quăng exception và thực thi chuẩn xác)
        assertDoesNotThrow(() -> orderService.deleteOrder(1L));
        verify(orderRepository, times(1)).deleteById(1L);
    }
}
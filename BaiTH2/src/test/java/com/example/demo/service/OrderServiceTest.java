package com.example.demo.service;

import com.example.demo.entity.Order;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = new Order(1L, "Alice", "Laptop", 1, 1200.0);
    }

    @Test
    void getAllOrders_ReturnNonEmptyList() {
        List<Order> list = new ArrayList<>();
        list.add(sampleOrder);
        when(orderRepository.findAll()).thenReturn(list);

        List<Order> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_Found() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getCustomerName());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrderById_NotFound_ThrowException() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getOrderById(99L));
        verify(orderRepository, times(1)).findById(99L);
    }

    @Test
    void addOrder_Success() {
        Order newOrder = new Order(null, "Bob", "Mouse", 2, 50.0);
        Order savedOrder = new Order(2L, "Bob", "Mouse", 2, 50.0);
        when(orderRepository.save(newOrder)).thenReturn(savedOrder);

        Order result = orderService.addOrder(newOrder);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        verify(orderRepository, times(1)).save(newOrder);
    }

    @Test
    void updateOrder_Success() {
        Order updatedDetails = new Order(null, "Alice Updated", "Laptop Pro", 1, 1500.0);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(sampleOrder);

        Order result = orderService.updateOrder(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Alice Updated", result.getCustomerName());
        assertEquals("Laptop Pro", result.getProduct());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(sampleOrder);
    }

    @Test
    void deleteOrder_RemovesElement() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(orderRepository.deleteById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> orderService.deleteOrder(1L));

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }
}
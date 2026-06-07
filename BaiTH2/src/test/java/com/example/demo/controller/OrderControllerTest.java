package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(orderService);
    }

    @Test
    void testGetAllOrders_Success() {
        Order order1 = new Order(1L, "Alice", "Laptop", 1, 1200.0);
        Order order2 = new Order(2L, "Bob", "Phone", 2, 800.0);

        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        ResponseEntity<List<Order>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Alice", response.getBody().get(0).getCustomerName());
        assertEquals("Bob", response.getBody().get(1).getCustomerName());
    }

    @Test
    void testGetOrderById_Found() {
        Order order = new Order(1L, "Alice", "Laptop", 1, 1200.0);
        when(orderService.getOrderById(1L)).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Alice", response.getBody().getCustomerName());
        assertEquals("Laptop", response.getBody().getProduct());
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderService.getOrderById(99L)).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<Order> response = orderController.getOrderById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testPostOrder_Created() {
        Order orderInput = new Order(null, "Charlie", "Tablet", 1, 300.0);
        Order orderSaved = new Order(3L, "Charlie", "Tablet", 1, 300.0);

        when(orderService.addOrder(any(Order.class))).thenReturn(orderSaved);

        ResponseEntity<Order> response = orderController.addOrder(orderInput);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().getId());
        assertEquals("Charlie", response.getBody().getCustomerName());
    }

    @Test
    void testUpdateOrder_Success() {
        Order orderDetails = new Order(null, "Alice Updated", "Laptop Pro", 1, 1500.0);
        Order updatedOrder = new Order(1L, "Alice Updated", "Laptop Pro", 1, 1500.0);

        when(orderService.updateOrder(1L, orderDetails)).thenReturn(updatedOrder);

        ResponseEntity<Order> response = orderController.updateOrder(1L, orderDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Alice Updated", response.getBody().getCustomerName());
    }

    @Test
    void testDeleteOrder_Success() {
        assertDoesNotThrow(() -> orderController.deleteOrder(1L));

        ResponseEntity<Void> response = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllOrders_Success() throws Exception {
        // Given
        Order order1 = new Order(1L, "Alice", "Book A", 2, 30.0);
        Order order2 = new Order(2L, "Bob", "Book B", 1, 15.0);
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        // When & Then
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].customerName").value("Alice"))
                .andExpect(jsonPath("$[1].customerName").value("Bob"));
    }

    @Test
    void testGetOrderById_Found() throws Exception {
        // Given
        Order order = new Order(1L, "Alice", "Book A", 2, 30.0);
        when(orderService.getOrderById(1L)).thenReturn(order);

        // When & Then
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("Alice"));
    }

    @Test
    void testGetOrderById_NotFound() throws Exception {
        // Given
        when(orderService.getOrderById(99L)).thenThrow(new RuntimeException("Order not found with id: 99"));

        // When & Then
        mockMvc.perform(get("/api/orders/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order not found with id: 99"));
    }

    @Test
    void testPostOrder_Success() throws Exception {
        // Given
        Order inputOrder = new Order(null, "Charlie", "Book C", 3, 45.0);
        Order savedOrder = new Order(3L, "Charlie", "Book C", 3, 45.0);
        when(orderService.addOrder(any(Order.class))).thenReturn(savedOrder);

        // When & Then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.customerName").value("Charlie"));
    }
}
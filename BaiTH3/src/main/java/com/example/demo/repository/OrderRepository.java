package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private final List<Order> orderList = new ArrayList<>();
    private Long autoIncrementId = 1L;

    public List<Order> findAll() {
        return orderList;
    }

    public Optional<Order> findById(Long id) {
        return orderList.stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(autoIncrementId++);
            orderList.add(order);
        } else {
            // Logic cập nhật nếu đã có ID
            int index = -1;
            for (int i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).getId().equals(order.getId())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                orderList.set(index, order);
            }
        }
        return order;
    }

    public boolean deleteById(Long id) {
        return orderList.removeIf(o -> o.getId().equals(id));
    }
}
package com.example.demo.repository;

import com.example.demo.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {
    private final List<Order> orders = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Order> findAll() {
        return orders;
    }

    public Optional<Order> findById(Long id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(idGenerator.getAndIncrement());
            orders.add(order);
        } else {
            int index = -1;
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getId().equals(order.getId())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                orders.set(index, order);
            }
        }
        return order;
    }

    public boolean deleteById(Long id) {
        return orders.removeIf(o -> o.getId().equals(id));
    }
}
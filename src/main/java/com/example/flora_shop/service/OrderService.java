package com.example.flora_shop.service;

import com.example.flora_shop.domain.Order;
import com.example.flora_shop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long create(Order order) {
        orderRepository.save(order);
        return order.getId();
    }
}

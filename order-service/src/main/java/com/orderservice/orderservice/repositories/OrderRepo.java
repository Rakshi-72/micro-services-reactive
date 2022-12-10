package com.orderservice.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservice.orderservice.models.Order;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(Integer userId);
}

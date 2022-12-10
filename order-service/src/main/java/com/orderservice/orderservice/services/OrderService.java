package com.orderservice.orderservice.services;

import com.orderservice.orderservice.dto.OrderDto;
import com.orderservice.orderservice.dto.OrderResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<OrderResponse> fullFillOrder(Mono<OrderDto> dto);
    Flux<OrderResponse> getOrdersForUser(Integer userId);
}

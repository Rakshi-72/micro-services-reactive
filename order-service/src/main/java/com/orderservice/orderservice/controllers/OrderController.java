package com.orderservice.orderservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderservice.orderservice.dto.OrderDto;
import com.orderservice.orderservice.dto.OrderResponse;
import com.orderservice.orderservice.dto.OrderStatus;
import com.orderservice.orderservice.services.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/process")
    public Mono<ResponseEntity<OrderResponse>> processOrder(@RequestBody Mono<OrderDto> dto) {

        Mono<OrderResponse> entityMono = service.fullFillOrder(dto);
                return  entityMono.filter(o -> o.getStatus().equals(OrderStatus.COMPLETED))
                .map(ResponseEntity::ok)
                .switchIfEmpty(entityMono.map(o -> ResponseEntity.badRequest().body(o)));

    }

    @GetMapping("/user/{userId}")
    public Flux<OrderResponse> getOrdersForUserId(@PathVariable Integer userId){
        return service.getOrdersForUser(userId);
    }

}

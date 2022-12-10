package com.orderservice.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.orderservice.orderservice.clients.ProductClient;
import com.orderservice.orderservice.clients.UserClient;
import com.orderservice.orderservice.dto.OrderDto;
import com.orderservice.orderservice.dto.OrderResponse;
import com.orderservice.orderservice.dto.ProductDto;
import com.orderservice.orderservice.dto.UserDto;
import com.orderservice.orderservice.services.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

@SpringBootTest
class OrderServiceApplicationTests {
	@Autowired
	private UserClient userClient;
	@Autowired
	private ProductClient productClient;
	@Autowired
	private OrderService service;

	@Test
	void orderTest() {

		Flux<OrderResponse> flux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
				.map(this::getOrderDto)
				.flatMap(dto -> service.fullFillOrder(Mono.just(dto)))
				.doOnNext(System.out::println);

		StepVerifier
				.create(flux)
				.expectNextCount(5)
				.verifyComplete();

	}

	private OrderDto getOrderDto(Tuple2<UserDto, ProductDto> tuple) {
		return new OrderDto(tuple.getT1().getId(), tuple.getT2().getProductId());
	}

}

package com.orderservice.orderservice.clients;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.orderservice.orderservice.dto.TransactionDto;
import com.orderservice.orderservice.dto.TransactionResponse;
import com.orderservice.orderservice.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    private final WebClient client;

    public UserClient(@Value("${user.service.url}") String url) {
        this.client = WebClient
                .builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TransactionResponse> doTransaction(TransactionDto transactionDto) {
        return client
                .post()
                .uri("transaction")
                .bodyValue(transactionDto).retrieve()
                .bodyToMono(TransactionResponse.class);
    }

    public Flux<UserDto> getAllUsers() {
        return client
                .get()
                .uri(URI.create("http://localhost:8092/api/user/all"))
                .retrieve()
                .bodyToFlux(UserDto.class);
    }
}

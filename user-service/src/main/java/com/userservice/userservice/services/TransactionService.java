package com.userservice.userservice.services;

import org.springframework.http.ResponseEntity;

import com.userservice.userservice.dtos.TransactionDto;
import com.userservice.userservice.dtos.TransactionResponse;
import com.userservice.userservice.models.UserTransaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<ResponseEntity<TransactionResponse>> createTransaction(final TransactionDto dto);

    Flux<UserTransaction> findAllTransactions(Integer userId);

}
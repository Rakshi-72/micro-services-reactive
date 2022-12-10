package com.userservice.userservice.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.userservice.userservice.models.UserTransaction;

import reactor.core.publisher.Flux;

public interface TransactionRepo extends ReactiveCrudRepository<UserTransaction, Integer> {

    Flux<UserTransaction> findByUserId(Integer userId);

}

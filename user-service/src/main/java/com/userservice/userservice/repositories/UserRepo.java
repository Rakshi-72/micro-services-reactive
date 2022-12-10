package com.userservice.userservice.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.userservice.userservice.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveCrudRepository<User, Integer> {

    /**
     * Find all users whose name contains the given key, ignoring case.
     * 
     * @param key The search key.
     * @return Flux<User>
     */
    Flux<User> findByNameContainingIgnoreCase(String key);

    /**
     * "Update the balance of a user if the user has enough balance to cover the
     * amount."
     * The above function is a good example of how to use the reactive driver to
     * implement a
     * transaction
     * 
     * @param userId The userId of the user whose balance is to be updated.
     * @param amount The amount to be deducted from the user's balance.
     * @return A Mono<Boolean>
     */
    @Modifying
    @Query("update users set balance = balance - ?2 where id = ?1 and balance >= ?2")
    Mono<Boolean> updateUserBalance(Integer userId, Integer amount);

}

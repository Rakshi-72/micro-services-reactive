package com.userservice.userservice.services;

import org.springframework.http.ResponseEntity;

import com.userservice.userservice.dtos.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserDto> getUsers();

    Mono<ResponseEntity<UserDto>> getUserById(Integer userId);

    Mono<ResponseEntity<Void>> deleteById(Integer userId);

    Mono<ResponseEntity<UserDto>> saveUser(Mono<UserDto> user);

    Mono<ResponseEntity<UserDto>> updateUser(Mono<UserDto> user, Integer userId);

    Flux<UserDto> searchUsersByName(String key);
}

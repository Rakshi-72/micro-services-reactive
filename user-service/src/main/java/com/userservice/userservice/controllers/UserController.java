package com.userservice.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.userservice.dtos.UserDto;
import com.userservice.userservice.services.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/all")
    public Flux<UserDto> getAllUsers() {
        return service.getUsers();
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable Integer userId) {
        return service.getUserById(userId);
    }

    @GetMapping("/search")
    public Flux<UserDto> searchUsersByName(@RequestParam String key) {
        return service.searchUsersByName(key);
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<UserDto>> saveUser(@RequestBody Mono<UserDto> dto) {
        return service.saveUser(dto);
    }

    @PutMapping("/update/{userId}")
    public Mono<ResponseEntity<UserDto>> updateUser(@RequestBody Mono<UserDto> dto, @PathVariable Integer userId) {
        return service.updateUser(dto, userId);
    }

    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Integer userId) {
        return service.deleteById(userId);
    }
}
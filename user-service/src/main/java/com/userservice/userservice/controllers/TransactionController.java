package com.userservice.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.userservice.dtos.TransactionDto;
import com.userservice.userservice.dtos.TransactionResponse;
import com.userservice.userservice.models.UserTransaction;
import com.userservice.userservice.services.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user/transaction")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @PostMapping
    public Mono<ResponseEntity<TransactionResponse>> doTransaction(@RequestBody Mono<TransactionDto> dto) {
        return dto.flatMap(service::createTransaction);
    }

    static Boolean b = false;

    @GetMapping("/{userId}")
    public ResponseEntity<Flux<UserTransaction>> getAllTransactionByUser(@PathVariable Integer userId) {

        Flux<UserTransaction> flux = service.findAllTransactions(userId);

        flux.hasElements().subscribe(bool -> b = bool);
        if (b)
            return ResponseEntity.ok(flux);
        return ResponseEntity.badRequest().build();

    }
}
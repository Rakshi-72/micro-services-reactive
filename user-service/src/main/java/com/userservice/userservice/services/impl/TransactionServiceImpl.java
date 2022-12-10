package com.userservice.userservice.services.impl;

import com.userservice.userservice.configs.MapperUtil;
import com.userservice.userservice.dtos.TransactionDto;
import com.userservice.userservice.dtos.TransactionResponse;
import com.userservice.userservice.dtos.TransactionStatus;
import com.userservice.userservice.models.UserTransaction;
import com.userservice.userservice.repositories.TransactionRepo;
import com.userservice.userservice.repositories.UserRepo;
import com.userservice.userservice.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    private UserRepo userRepo;
    private TransactionRepo transactionRepo;
    private MapperUtil mapper;

    public TransactionServiceImpl(UserRepo userRepo, TransactionRepo transactionRepo, MapperUtil mapper) {
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
        this.mapper = mapper;
    }

    @Override
    public Flux<UserTransaction> findAllTransactions(Integer userId) {
        return transactionRepo.findByUserId(userId);
    }

    /**
     * If the user's balance is updated, then save the transaction and return a
     * response with status
     * APPROVED, otherwise return a response with status DECLINED.
     *
     * @param dto The transaction data transfer object.
     * @return A Mono<ResponseEntity<TransactionResponse>>
     */
    @Override
    public Mono<ResponseEntity<TransactionResponse>> createTransaction(TransactionDto dto) {
        return userRepo.updateUserBalance(dto.getUserId(), dto.getAmount())
                .filter(Boolean::booleanValue)
                .map(isIt -> mapper.dtoToUserTransactionModel(dto))
                .flatMap(transactionRepo::save)
                .map(ut -> mapper.dtoToResponse(dto))
                .doOnNext(tr -> tr.setStatus(TransactionStatus.APPROVED))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(
                        ResponseEntity.badRequest().body(getTR(dto)));

    }

    TransactionResponse getTR(TransactionDto dto) {
        TransactionResponse transactionResponse = mapper.dtoToResponse(dto);
        transactionResponse.setStatus(TransactionStatus.DECLINED);
        return transactionResponse;
    }

    /**
     * > We find a user by id, filter out users with insufficient balance, subtract
     * the amount from the
     * user's balance, save the user, filter out null users, map the dto to a
     * transaction model, save
     * the transaction, map the dto to a response, and return a 200 response if the
     * transaction was
     * successful, or a 400 response if the transaction was declined
     *
     * @param dto The transaction data transfer object.
     * @return A Mono<ResponseEntity<TransactionResponse>>
     */
    public Mono<ResponseEntity<TransactionResponse>> createTransactionMine(TransactionDto dto) {
        return userRepo
                .findById(dto.getUserId())
                .filter(user -> user.getBalance() >= dto.getAmount())
                .doOnNext(user -> user.setBalance(user.getBalance() - dto.getAmount()))
                .flatMap(userRepo::save)
                .map(user -> mapper.dtoToUserTransactionModel(dto))
                .flatMap(transactionRepo::save)
                .map(t -> mapper.dtoToResponse(dto))
                .doOnNext(t -> t.setStatus(TransactionStatus.APPROVED))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(
                        ResponseEntity.badRequest().body(getTR(dto)));

    }

}
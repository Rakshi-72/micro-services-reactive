package com.orderservice.orderservice.services.impl;

import org.springframework.stereotype.Service;

import com.orderservice.orderservice.Utils.Utils;
import com.orderservice.orderservice.clients.ProductClient;
import com.orderservice.orderservice.clients.UserClient;
import com.orderservice.orderservice.dto.OrderDto;
import com.orderservice.orderservice.dto.OrderResponse;
import com.orderservice.orderservice.dto.RequestContext;
import com.orderservice.orderservice.dto.TransactionDto;
import com.orderservice.orderservice.repositories.OrderRepo;
import com.orderservice.orderservice.services.OrderService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo repo;
    private final ProductClient productClient;
    private final UserClient userClient;

    private final Utils utils;

    /**
     * It takes an order, gets the product details, gets the transaction details,
     * saves the order, and
     * returns the response
     *
     * @param dto Mono&lt;OrderDto&gt;
     * @return A Mono of OrderResponse
     */

    @Override
    public Mono<OrderResponse> fullFillOrder(Mono<OrderDto> dto) {

        return dto.map(RequestContext::new)
                .flatMap(this::getProductDetails)
                .map(this::getTransactionDto)
                .flatMap(this::getTransactionDetails)
                .map(new Utils()::toOrder)
                .map(repo::save) // bloking driver
                .map(new Utils()::toResponse)
                .subscribeOn(Schedulers.boundedElastic());

    }

    @Override
    public Flux<OrderResponse> getOrdersForUser(Integer userId) {
        return Flux.fromStream(() -> repo.findByUserId(userId).stream())
                .map(utils::toResponse)
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Get the product details from the product service and set the product details
     * in the context
     * object.
     *
     * @param context RequestContext
     * @return A Mono&lt;RequestContext&gt;
     */
    private Mono<RequestContext> getProductDetails(RequestContext context) {
        return productClient.getProductById(context.getOrderDto().getProductId())
                .doOnNext(context::setProductDto)
                .thenReturn(context);
    }

    private Mono<RequestContext> getTransactionDetails(RequestContext context) {

        return userClient.doTransaction(context.getTransactionDto()).doOnNext(context::setTransactionResponse)
                .thenReturn(context);

    }

    private RequestContext getTransactionDto(RequestContext context) {
        TransactionDto transactionDto = new TransactionDto(context.getOrderDto().getUserId(),
                context.getProductDto().getPrice());
        context.setTransactionDto(transactionDto);
        return context;
    }
}

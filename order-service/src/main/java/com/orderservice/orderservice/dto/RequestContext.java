package com.orderservice.orderservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class RequestContext {

    private final OrderDto orderDto;
    private TransactionDto transactionDto;
    private ProductDto productDto;
    private TransactionResponse transactionResponse;

}
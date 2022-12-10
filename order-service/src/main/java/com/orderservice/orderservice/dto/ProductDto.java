package com.orderservice.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private String productId;

    private String descriptions;

    private Double price;
}

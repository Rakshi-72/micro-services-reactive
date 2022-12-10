package com.orderservice.orderservice.models;

import javax.persistence.*;

import com.orderservice.orderservice.dto.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private String productId;
    private Integer userId;
    private Integer amount;
    private OrderStatus status;
}

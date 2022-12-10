package com.orderservice.orderservice.Utils;

import org.springframework.stereotype.Component;

import com.orderservice.orderservice.dto.OrderResponse;
import com.orderservice.orderservice.dto.OrderStatus;
import com.orderservice.orderservice.dto.RequestContext;
import com.orderservice.orderservice.dto.TransactionResponse;
import com.orderservice.orderservice.dto.TransactionStatus;
import com.orderservice.orderservice.models.Order;

@Component
public class Utils {

    /**
     * It takes an Order object and returns an OrderResponse object
     * 
     * @param order The order object that we want to convert to a response object.
     * @return A response object
     */
    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setStatus(order.getStatus());
        response.setAmount(order.getAmount());
        response.setProductId(order.getProductId());
        response.setUserId(order.getUserId());
        return response;
    }

    /**
     * It takes a RequestContext object and returns an Order object
     * 
     * @param context This is the context object that contains the request
     *                parameters.
     * @return A new Order object is being returned.
     */
    public Order toOrder(RequestContext context) {
        Order response = new Order();

        TransactionResponse transactionResponse = context.getTransactionResponse();

        if (transactionResponse.getStatus().equals(TransactionStatus.APPROVED))
            response.setStatus(OrderStatus.COMPLETED);
        else
            response.setStatus(OrderStatus.FAILED);

        response.setAmount(transactionResponse.getAmount());
        response.setProductId(context.getProductDto().getProductId());
        response.setUserId(transactionResponse.getUserId());
        return response;
    }

}

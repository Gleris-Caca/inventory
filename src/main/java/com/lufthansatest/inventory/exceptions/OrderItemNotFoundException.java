package com.lufthansatest.inventory.exceptions;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String message) {
        super(message); //"Item not found in order"
    }
}

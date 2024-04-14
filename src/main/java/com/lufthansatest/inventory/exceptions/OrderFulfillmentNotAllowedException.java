package com.lufthansatest.inventory.exceptions;

public class OrderFulfillmentNotAllowedException extends RuntimeException {
    public OrderFulfillmentNotAllowedException(String message) {
        super(message);
    }
}
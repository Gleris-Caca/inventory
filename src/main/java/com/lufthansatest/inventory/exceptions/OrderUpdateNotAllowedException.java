package com.lufthansatest.inventory.exceptions;

public class OrderUpdateNotAllowedException extends RuntimeException {

    public OrderUpdateNotAllowedException() {
        super();
    }

    public OrderUpdateNotAllowedException(String message) {
        super(message);
    }

    public OrderUpdateNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderUpdateNotAllowedException(Throwable cause) {
        super(cause);
    }
}
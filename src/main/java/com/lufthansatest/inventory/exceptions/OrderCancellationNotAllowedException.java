package com.lufthansatest.inventory.exceptions;

public class OrderCancellationNotAllowedException extends RuntimeException {

    public OrderCancellationNotAllowedException() {
        super();
    }

    public OrderCancellationNotAllowedException(String message) {
        super(message);
    }

    public OrderCancellationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderCancellationNotAllowedException(Throwable cause) {
        super(cause);
    }
}
package com.lufthansatest.inventory.exceptions;

public class OrderSubmissionNotAllowedException extends RuntimeException {

    public OrderSubmissionNotAllowedException() {
        super();
    }

    public OrderSubmissionNotAllowedException(String message) {
        super(message);
    }

    public OrderSubmissionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderSubmissionNotAllowedException(Throwable cause) {
        super(cause);
    }
}
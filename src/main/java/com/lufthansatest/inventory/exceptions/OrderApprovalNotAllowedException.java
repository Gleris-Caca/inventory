package com.lufthansatest.inventory.exceptions;

public class OrderApprovalNotAllowedException extends RuntimeException {

    public OrderApprovalNotAllowedException() {
        super();
    }

    public OrderApprovalNotAllowedException(String message) {
        super(message);
    }

    public OrderApprovalNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderApprovalNotAllowedException(Throwable cause) {
        super(cause);
    }
}
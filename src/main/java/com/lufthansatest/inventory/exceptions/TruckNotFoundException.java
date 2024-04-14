package com.lufthansatest.inventory.exceptions;

public class TruckNotFoundException extends RuntimeException {

    public TruckNotFoundException(String message) {
        super(message); //"Truck not found with id: " + id)
    }
}

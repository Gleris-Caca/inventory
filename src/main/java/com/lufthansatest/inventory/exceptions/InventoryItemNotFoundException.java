package com.lufthansatest.inventory.exceptions;

public class InventoryItemNotFoundException extends RuntimeException {
    public InventoryItemNotFoundException(String message) {
        super(message); //"Inventory item not found with ID: " + itemId)
    }
}
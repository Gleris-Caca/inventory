package com.lufthansatest.inventory.model.dto;

import com.lufthansatest.inventory.model.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemDTO extends OrderItem {
    private Long id;
    private String itemName;
    private int requestedQuantity;

    public OrderItemDTO(Long id, String itemName, int requestedQuantity) {
        this.id = id;
        this.itemName = itemName;
        this.requestedQuantity = requestedQuantity;
    }

    public OrderItemDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
}

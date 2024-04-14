package com.lufthansatest.inventory.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private InventoryItem item;

    @Column(name = "requested_quantity")
    private int requestedQuantity;

    public OrderItem(Long id, Order order, InventoryItem item, int requestedQuantity) {
        this.id = id;
        this.order = order;
        this.item = item;
        this.requestedQuantity = requestedQuantity;
    }

    public OrderItem() {

    }

    public OrderItem(InventoryItem item, int quantity) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public InventoryItem getItem() {
        return item;
    }

    public void setItem(InventoryItem item) {
        this.item = item;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }


    public void setItemName(String itemName) {
    }

    public String getItemName() {
        return null;
    }
}
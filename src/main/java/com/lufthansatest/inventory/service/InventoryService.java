package com.lufthansatest.inventory.service;

import com.lufthansatest.inventory.model.entity.InventoryItem;
import com.lufthansatest.inventory.model.entity.Order;

import java.util.List;

public interface InventoryService {
    InventoryItem findInventoryItemById(Long itemId);

    List<InventoryItem> getAllInventoryItems();

    InventoryItem getInventoryItemById(Long id);

    InventoryItem createInventoryItem(InventoryItem item);

    InventoryItem updateInventoryItem(Long id, InventoryItem newItem);

    void deleteInventoryItem(Long id);

    void updateItemQuantityForScheduledDelivery(Order order);
}
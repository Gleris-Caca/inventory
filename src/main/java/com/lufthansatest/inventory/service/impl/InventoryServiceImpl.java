package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.exceptions.InventoryItemNotFoundException;
import com.lufthansatest.inventory.model.entity.InventoryItem;
import com.lufthansatest.inventory.model.entity.Order;
import com.lufthansatest.inventory.repository.InventoryItemRepository;
import com.lufthansatest.inventory.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryItemRepository inventoryItemRepository;

    @Override
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }

    @Override
    public InventoryItem getInventoryItemById(Long id) {
        return inventoryItemRepository.findById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException("Inventory item not found with id: " + id));
    }

    @Override
    public InventoryItem createInventoryItem(InventoryItem item) {
        return inventoryItemRepository.save(item);
    }
    @Override
    public InventoryItem updateInventoryItem(Long id, InventoryItem newItem) {
        Optional<InventoryItem> optionalItem = inventoryItemRepository.findById(id);
        if (optionalItem.isPresent()) {
            InventoryItem existingItem = optionalItem.get();
            // Update existing item properties
            existingItem.setItemName(newItem.getItemName());
            existingItem.setUnitPrice(newItem.getUnitPrice());
            existingItem.setQuantity(newItem.getQuantity());
            // Save and return the updated item
            return inventoryItemRepository.save(existingItem);
        } else {
            throw new InventoryItemNotFoundException("Inventory item not found with id: " + id);
        }
    }
    @Override
    public void deleteInventoryItem(Long id) {
        inventoryItemRepository.deleteById(id);
    }

    @Override
    public void updateItemQuantityForScheduledDelivery(Order order) {

    }

    @Override
    public InventoryItem findInventoryItemById(Long itemId) {
        return inventoryItemRepository.findById(itemId)
                .orElseThrow(() -> new InventoryItemNotFoundException("Inventory item not found with id: " + itemId));
    }

}
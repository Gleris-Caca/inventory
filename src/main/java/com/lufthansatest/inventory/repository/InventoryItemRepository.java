package com.lufthansatest.inventory.repository;

import com.lufthansatest.inventory.model.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    // Define custom query methods if needed
}
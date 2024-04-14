package com.lufthansatest.inventory.controller;

import com.lufthansatest.inventory.model.entity.InventoryItem;
import com.lufthansatest.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/inventory")
@RequiredArgsConstructor
@Schema
public class InventoryController {

    private final InventoryService inventoryService;

    //e testuar
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @GetMapping("/items")
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryService.getAllInventoryItems();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }
    //e testuar
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @GetMapping("/items/{id}")
    public ResponseEntity<InventoryItem> getInventoryItemById(@PathVariable Long id) {
        InventoryItem inventoryItem = inventoryService.getInventoryItemById(id);
        return new ResponseEntity<>(inventoryItem, HttpStatus.OK);
    }

    //e testuar
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @PostMapping("/items")
    public ResponseEntity<InventoryItem> createInventoryItem(@RequestBody InventoryItem item) {
        InventoryItem createdItem = inventoryService.createInventoryItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    //e testuar
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @PutMapping("/items/{id}")
    public ResponseEntity<InventoryItem> updateInventoryItem(@PathVariable Long id, @RequestBody InventoryItem newItem) {
        InventoryItem updatedItem = inventoryService.updateInventoryItem(id, newItem);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
    //e testuar
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryService.deleteInventoryItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
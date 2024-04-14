package com.lufthansatest.inventory.mapper;

import com.lufthansatest.inventory.model.dto.InventoryItemDTO;
import com.lufthansatest.inventory.model.entity.InventoryItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryItemMapper extends AbstractMapper<InventoryItem, InventoryItemDTO> {

    @Override
    public InventoryItem toEntity(InventoryItemDTO dto) {
        if (dto == null) {
            return null;
        }

        log.info("Converting InventoryItemDto to Entity");
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(dto.getId());
        inventoryItem.setItemName(dto.getItemName());
        inventoryItem.setQuantity(dto.getQuantity());
        inventoryItem.setUnitPrice(dto.getUnitPrice());
        // You may need to map other fields if necessary
        return inventoryItem;
    }

    @Override
    public InventoryItemDTO toDto(InventoryItem entity) {
        if (entity == null) {
            return null;
        }

        log.info("Converting InventoryItem to DTO");
        InventoryItemDTO inventoryItemDto = new InventoryItemDTO();
        inventoryItemDto.setId(entity.getId());
        inventoryItemDto.setItemName(entity.getItemName());
        inventoryItemDto.setQuantity(entity.getQuantity());
        inventoryItemDto.setUnitPrice(entity.getUnitPrice());
        // You may need to map other fields if necessary
        return inventoryItemDto;
    }
}
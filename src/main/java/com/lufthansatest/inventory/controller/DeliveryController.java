package com.lufthansatest.inventory.controller;

import com.lufthansatest.inventory.exceptions.DeliverySchedulingException;
import com.lufthansatest.inventory.model.entity.Truck;
import com.lufthansatest.inventory.service.DeliveryService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/delivery")
@RequiredArgsConstructor
@Schema
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleDeliveryForApprovedOrder(@RequestParam Long orderId,
                                                                   @RequestParam Date deliveryDate,
                                                                   @RequestBody List<Truck> trucks) {
        try {
            deliveryService.scheduleDeliveryForApprovedOrder(orderId, deliveryDate, trucks);
            return ResponseEntity.ok("Delivery scheduled successfully");
        } catch (DeliverySchedulingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}

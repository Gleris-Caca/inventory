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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/delivery")
@RequiredArgsConstructor
@Schema
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @PostMapping("/schedule/{orderId}")
    public ResponseEntity<String> scheduleDeliveryForApprovedOrder(
            @PathVariable Long orderId) {
        try {
            String date = "2024-04-14";
            Date parsedDeliveryDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
            deliveryService.scheduleDeliveryForApprovedOrder(orderId, parsedDeliveryDate, null); // trucks are not passed in the URL
            return ResponseEntity.ok("Delivery scheduled successfully");
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid delivery date format");
        } catch (DeliverySchedulingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

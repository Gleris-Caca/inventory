package com.lufthansatest.inventory.controller;

import com.lufthansatest.inventory.model.entity.Truck;
import com.lufthansatest.inventory.service.TruckService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/trucks")
@RequiredArgsConstructor
@Schema
public class TruckController {
    private final TruckService truckService;

    //e testuar
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @GetMapping()
    public ResponseEntity<List<Truck>> getAllTrucks() {
        List<Truck> trucks = truckService.getAllTrucks();
        return ResponseEntity.ok(trucks);
    }

    //e testuar
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Truck> getTruckById(@PathVariable Long id) {
        Truck truck = truckService.getTruckById(id);
        return ResponseEntity.ok(truck);
    }

    // e testuar me postman
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @PostMapping("/createTruck")
    public ResponseEntity<Truck> createTruck(@RequestBody Truck truck) {
        Truck createdTruck = truckService.createTruck(truck);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTruck);
    }

    //e testuar
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Truck> updateTruck(@PathVariable Long id, @RequestBody Truck newTruck) {
        Truck updatedTruck = truckService.updateTruck(id, newTruck);
        return ResponseEntity.ok(updatedTruck);
    }

    //e testuar
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        truckService.deleteTruck(id);
        return ResponseEntity.noContent().build();
    }
}

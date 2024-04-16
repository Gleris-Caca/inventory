package com.lufthansatest.inventory.service.impl;



import com.lufthansatest.inventory.exceptions.DeliverySchedulingException;
import com.lufthansatest.inventory.exceptions.TruckNotFoundException;
import com.lufthansatest.inventory.model.entity.Truck;
import com.lufthansatest.inventory.repository.TruckRepository;
import com.lufthansatest.inventory.service.TruckService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TruckServiceImpl  implements TruckService {
    private final TruckRepository truckRepository;



    @Override
    public void validateAndScheduleDelivery(Truck truck, Date deliveryDate) {

    }

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public Truck getTruckById(Long id) {
        return truckRepository.findById(id)
                .orElseThrow(() -> new TruckNotFoundException("Truck not found with id: " + id));
    }

    public Truck createTruck(Truck truck) {
        return truckRepository.save(truck);
    }

    public Truck updateTruck(Long id, Truck newTruck) {
        Optional<Truck> optionalTruck = truckRepository.findById(id);
        if (optionalTruck.isPresent()) {
            Truck existingTruck = optionalTruck.get();
            // Update existing truck properties
            existingTruck.setLicensePlate(newTruck.getLicensePlate());
            existingTruck.setChassisNumber(newTruck.getChassisNumber());
            existingTruck.setCapacity(newTruck.getCapacity());
            // Save and return the updated truck
            return truckRepository.save(existingTruck);
        } else {
            throw new TruckNotFoundException("Truck not found with id: " + id);
        }
    }

    public void deleteTruck(Long id) {
        truckRepository.deleteById(id);
    }

    @Override
    public boolean isTruckAvailable(Truck truck, Date deliveryDate) {
        return false;
    }

    @Override
    public boolean hasCapacityForDelivery(Truck truck, int size) {
        return false;
    }



}
package com.lufthansatest.inventory.service;

import com.lufthansatest.inventory.model.entity.Truck;

import java.util.Date;
import java.util.List;

public interface TruckService {

    void validateAndScheduleDelivery(Truck truck, Date deliveryDate);

    List<Truck> getAllTrucks();

    Truck getTruckById(Long id);

    Truck createTruck(Truck truck);

    Truck updateTruck(Long id, Truck newTruck);

    void deleteTruck(Long id);

    boolean isTruckAvailable(Truck truck, Date deliveryDate);

    boolean hasCapacityForDelivery(Truck truck, int size);

    //  void validateTruckAvailabilityAndCapacity(Truck truck, Date deliveryDate);
}

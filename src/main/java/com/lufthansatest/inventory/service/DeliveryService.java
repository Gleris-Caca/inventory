package com.lufthansatest.inventory.service;

import com.lufthansatest.inventory.model.entity.Truck;

import java.util.Date;
import java.util.List;

public interface DeliveryService {


    void scheduleDeliveryForApprovedOrder(Long orderId, Date deliveryDate, List<Truck> trucks);
}


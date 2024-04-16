package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.exceptions.DeliverySchedulingException;
import com.lufthansatest.inventory.exceptions.NotEnoughInventoryException;
import com.lufthansatest.inventory.model.entity.InventoryItem;
import com.lufthansatest.inventory.model.entity.Order;
import com.lufthansatest.inventory.model.entity.OrderItem;
import com.lufthansatest.inventory.model.entity.Truck;
import com.lufthansatest.inventory.model.enums.OrderStatus;
import com.lufthansatest.inventory.repository.InventoryItemRepository;
import com.lufthansatest.inventory.service.DeliveryService;
import com.lufthansatest.inventory.service.InventoryService;
import com.lufthansatest.inventory.service.OrderService;
import com.lufthansatest.inventory.service.TruckService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final OrderService orderService;
    private final TruckService truckService;
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryService inventoryService;


    public void scheduleDeliveryForApprovedOrder(Long orderId, Date deliveryDate, List<Truck> trucks) throws DeliverySchedulingException {

        Order order = orderService.getOrderById(orderId);

        if (!order.getStatus().equals(OrderStatus.APPROVED)) {
            throw new DeliverySchedulingException("Delivery can only be scheduled for approved orders");
        }

        // e djela pushim
        if (isSunday(deliveryDate)) {
            throw new DeliverySchedulingException("Truck drivers do not work on Sundays. Choose a different date.");
        }

        for (Truck truck : trucks) {
            if (!truckService.isTruckAvailable(truck, deliveryDate)) {
                throw new DeliverySchedulingException("Truck " + truck.getLicensePlate() + " is not available for delivery on " + deliveryDate);
            }
            if (!truckService.hasCapacityForDelivery(truck, order.getItems().size())) {
                throw new DeliverySchedulingException("Truck " + truck.getLicensePlate() + " does not have enough capacity for the delivery");
            }
        }

        order.setStatus(OrderStatus.UNDER_DELIVERY);
        order.setDeliveryDate(deliveryDate);

        inventoryService.updateItemQuantityForScheduledDelivery(order);

        orderService.updateOrder(order);
    }

    private boolean isSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SUNDAY;
    }
}

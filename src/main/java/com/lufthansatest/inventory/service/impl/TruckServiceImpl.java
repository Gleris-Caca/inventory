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
  //  private final DeliveryRepository deliveryRepository;

    //TODO jo e plote shikoje!!!!!!!!!!!11
//    public void validateAndScheduleDelivery(Truck truck, Date deliveryDate) throws DeliverySchedulingException {
//        // Check if the truck is available for delivery on the specified date
//        boolean isTruckAvailable = isTruckAvailableForDelivery(truck, deliveryDate);
//        if (!isTruckAvailable) {
//            throw new DeliverySchedulingException("Truck is not available for delivery on the specified date");
//        }
//
//        // Check if the truck has the capacity to carry the items
//        boolean hasCapacity = hasTruckCapacity(truck);
//        if (!hasCapacity) {
//            throw new DeliverySchedulingException("Truck does not have enough capacity to carry the items");
//        }
//
//    }
//
//   private boolean isTruckAvailableForDelivery(Truck truck, Date deliveryDate) {
//     // Check if the truck has any delivery scheduled on the specified date
//        return deliveryRepository.countByTruckAndDeliveryDate(truck, deliveryDate) == 0;
//    }

    private boolean hasTruckCapacity(Truck truck) {
        // Check if the truck has enough capacity to carry the items
        return truck.getCapacity() >= 10; // Assuming capacity is defined in terms of item count
    }

    public boolean isSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SUNDAY;
    }


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
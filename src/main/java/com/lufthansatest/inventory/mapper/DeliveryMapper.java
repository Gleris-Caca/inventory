//package com.lufthansatest.inventory.mapper;
//
//import com.lufthansatest.inventory.model.dto.DeliveryDTO;
//import com.lufthansatest.inventory.model.entity.Delivery;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@Slf4j
//public class DeliveryMapper extends AbstractMapper<Delivery, DeliveryDTO> {
//
//    @Override
//    public Delivery toEntity(DeliveryDTO deliveryDTO) {
//        if (deliveryDTO == null) {
//            return null;
//        }
//
//        log.info("Converting DeliveryDTO to Entity");
//        Delivery delivery = new Delivery();
//        delivery.setId(deliveryDTO.getId());
//        delivery.setOrderId(deliveryDTO.getOrderId());
//        delivery.setDeliveryDate(deliveryDTO.getDeliveryDate());
//        //delivery.setTrucks(deliveryDTO.getTruckIds());
//        // You may need to map other fields if necessary
//        return delivery;
//    }
//
//    @Override
//    public DeliveryDTO toDto(Delivery delivery) {
//        if (delivery == null) {
//            return null;
//        }
//
//        log.info("Converting Delivery to DTO");
//        DeliveryDTO deliveryDTO = new DeliveryDTO();
//        deliveryDTO.setId(delivery.getId());
//        deliveryDTO.setOrderId(delivery.getOrderId());
//        deliveryDTO.setDeliveryDate(delivery.getDeliveryDate());
//        //deliveryDTO.setTruckIds(delivery.getTruckIds());
//        // You may need to map other fields if necessary
//        return deliveryDTO;
//    }
//
//    public List<DeliveryDTO> toDtoList(List<Delivery> deliveries) {
//        return deliveries.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
//}
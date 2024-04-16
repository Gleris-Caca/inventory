//package com.lufthansatest.inventory.model.dto;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.lufthansatest.inventory.model.entity.Truck;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//import java.util.Date;
//import java.util.List;
//
//@Data
//public class DeliveryDTO {
//
//    private Long id;
//
//    @NotNull
//    private Long orderId;
//
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @NotNull
//    private Date deliveryDate;
//
//    @NotNull
//    private List<Long> truckIds;
//
//    public DeliveryDTO() {
//    }
//
//    public DeliveryDTO(Long id, Long orderId, Date deliveryDate, List<Long> truckIds) {
//        this.id = id;
//        this.orderId = orderId;
//        this.deliveryDate = deliveryDate;
//        this.truckIds = truckIds;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public Date getDeliveryDate() {
//        return deliveryDate;
//    }
//
//    public void setDeliveryDate(Date deliveryDate) {
//        this.deliveryDate = deliveryDate;
//    }
//
//
//
//    public void setTruckIds(List<Long> truckIds) {
//        this.truckIds = truckIds;
//    }
//}
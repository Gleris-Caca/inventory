//package com.lufthansatest.inventory.model.entity;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@Table(name = "deliveries")
//public class Delivery {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    @Column(name = "delivery_date")
//    private Date deliveryDate;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "delivery_trucks",
//            joinColumns = @JoinColumn(name = "delivery_id"),
//            inverseJoinColumns = @JoinColumn(name = "truck_id")
//    )
//    private List<Truck> trucks;
//    @Column(name = "order_id")
//    private Long orderId;
//
//    public Delivery() {
//    }
//
//    public Delivery(Order order, Date deliveryDate, List<Truck> trucks) {
//        this.order = order;
//        this.deliveryDate = deliveryDate;
//        this.trucks = trucks;
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
//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
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
//    public List<Truck> getTrucks() {
//        return trucks;
//    }
//
//    public void setTrucks(List<Truck> trucks) {
//        this.trucks = trucks;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public Long getOrderId() {
//        return orderId;
//    }
//
//
//    public List<Truck> getTruckIds() {
//        return trucks;
//    }
//}
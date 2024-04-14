package com.lufthansatest.inventory.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chassis_number", unique = true)
    private String chassisNumber;

    @Column(name = "license_plate", unique = true)
    private String licensePlate;

    @Column(name ="capacity", unique = true)
    private int capacity;

    public Truck(Long id, String chassisNumber, String licensePlate, int capacity) {
        this.id = id;
        this.chassisNumber = chassisNumber;
        this.licensePlate = licensePlate;
        this.capacity=capacity;
    }

    public Truck() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
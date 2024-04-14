package com.lufthansatest.inventory.model.dto;

import lombok.Data;

@Data
public class TruckDTO {
    private Long id;
    private String chassisNumber;
    private String licensePlate;

    public TruckDTO(Long id, String chassisNumber, String licensePlate) {
        this.id = id;
        this.chassisNumber = chassisNumber;
        this.licensePlate = licensePlate;
    }

    public TruckDTO() {

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
}

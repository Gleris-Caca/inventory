package com.lufthansatest.inventory.mapper;

import com.lufthansatest.inventory.model.dto.TruckDTO;
import com.lufthansatest.inventory.model.entity.Truck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TruckMapper extends AbstractMapper<Truck, TruckDTO> {

    @Override
    public Truck toEntity(TruckDTO dto) {
        if (dto == null) {
            return null;
        }

        log.info("Converting TruckDto to Entity");
        Truck truck = new Truck();
        truck.setId(dto.getId());
        truck.setChassisNumber(dto.getChassisNumber());
        truck.setLicensePlate(dto.getLicensePlate());
        return truck;
    }

    @Override
    public TruckDTO toDto(Truck entity) {
        if (entity == null) {
            return null;
        }

        log.info("Converting Truck to DTO");
        TruckDTO truckDto = new TruckDTO();
        truckDto.setId(entity.getId());
        truckDto.setChassisNumber(entity.getChassisNumber());
        truckDto.setLicensePlate(entity.getLicensePlate());
        return truckDto;
    }
}
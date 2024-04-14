package com.lufthansatest.inventory.mapper;

import org.springframework.stereotype.Component;

@Component
public abstract class AbstractMapper<ENTITY,DTO> {
    public abstract ENTITY toEntity(DTO dto);  //logic for mapping a DTO object to an ENTITY
    public abstract DTO toDto(ENTITY entity); //logic for mapping an ENTITY object to a DTO object
}
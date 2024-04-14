package com.lufthansatest.inventory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class BaseResponse {
    private List<String> messages;
}
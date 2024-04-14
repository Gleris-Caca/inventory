package com.lufthansatest.inventory.mapper;

import com.lufthansatest.inventory.model.dto.OrderDTO;
import com.lufthansatest.inventory.model.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OrderMapper extends AbstractMapper<Order, OrderDTO> {

    @Override
    public Order toEntity(OrderDTO orderDto) {
        if (orderDto == null) {
            return null;
        }

        log.info("Converting OrderDto to Entity");
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setSubmittedDate(orderDto.getSubmittedDate());
        order.setStatus(orderDto.getStatus());
        // order.setItems(orderDto.getItems());
        order.setDeadlineDate(orderDto.getDeadlineDate());
        // You may need to map other fields if necessary
        return order;
    }

    @Override
    public OrderDTO toDto(Order order) {
        if (order == null) {
            return null;
        }

        log.info("Converting Order to DTO");
        OrderDTO orderDto = new OrderDTO();
        orderDto.setId(order.getId());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setSubmittedDate(order.getSubmittedDate());
        orderDto.setStatus(order.getStatus());
        //  orderDto.setItems(order.getItems());
        orderDto.setDeadlineDate(order.getDeadlineDate());
        // You may need to map other fields if necessary
        return orderDto;
    }

    public List<OrderDTO> toDtoList(List<Order> orders) {
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
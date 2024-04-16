package com.lufthansatest.inventory.mapper;

import com.lufthansatest.inventory.model.dto.OrderDTO;
import com.lufthansatest.inventory.model.dto.OrderItemDTO;
import com.lufthansatest.inventory.model.entity.Order;
import com.lufthansatest.inventory.model.entity.OrderItem;
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
        OrderItem orderItem;
        log.info("Converting OrderDto to Entity");
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setSubmittedDate(orderDto.getSubmittedDate());
        order.setStatus(orderDto.getStatus());
        order.setItems(orderDto.getItems());
        order.setDeadlineDate(orderDto.getDeadlineDate());
        order.setReason(orderDto.getReason());
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
        orderDto.setItems(order.getItems());
        orderDto.setDeadlineDate(order.getDeadlineDate());
        orderDto.setReason(orderDto.getReason());
        return orderDto;
    }

    public List<OrderDTO> toDtoList(List<Order> orders) {
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
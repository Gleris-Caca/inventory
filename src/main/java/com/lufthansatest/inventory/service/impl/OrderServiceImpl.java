package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.exceptions.*;
import com.lufthansatest.inventory.mapper.InventoryItemMapper;
import com.lufthansatest.inventory.mapper.OrderMapper;
import com.lufthansatest.inventory.model.dto.OrderDTO;
import com.lufthansatest.inventory.model.entity.Order;
import com.lufthansatest.inventory.model.enums.OrderStatus;
import com.lufthansatest.inventory.repository.OrderRepository;
import com.lufthansatest.inventory.service.InventoryService;
import com.lufthansatest.inventory.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private final InventoryItemMapper inventoryItemMapper;


    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO updatedOrderDTO) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Check if the order status allows updates
            if (order.getStatus().equals(OrderStatus.CREATED) || order.getStatus().equals(OrderStatus.DECLINED)) {
                // Update order details
                if (updatedOrderDTO.getOrderNumber() != null && !updatedOrderDTO.getOrderNumber().isEmpty()) {
                    order.setOrderNumber(updatedOrderDTO.getOrderNumber());
                }
                if (updatedOrderDTO.getSubmittedDate() != null) {
                    order.setSubmittedDate(updatedOrderDTO.getSubmittedDate());
                }
                if (updatedOrderDTO.getStatus() != null) {
                    order.setStatus(updatedOrderDTO.getStatus());
                }
                if (updatedOrderDTO.getDeadlineDate() != null) {
                    order.setDeadlineDate(updatedOrderDTO.getDeadlineDate());
                }
                if (updatedOrderDTO.getReason() != null) {
                    order.setReason(updatedOrderDTO.getReason());
                }
                if (updatedOrderDTO.getItems() != null) {
                    order.setItems(updatedOrderDTO.getItems());
                }

                // Save the updated order
                Order updatedOrder = orderRepository.save(order);
                return orderMapper.toDto(updatedOrder);
            } else {
                throw new GeneralException("Order cannot be updated because its status is not CREATED or DECLINED");
            }
        } else {
            throw new IllegalArgumentException("Order with id " + orderId + " not found");
        }
    }
    private void checkIfItCanUpdate(Order order) {
        if (!order.getStatus().equals(OrderStatus.CREATED) && !order.getStatus().equals(OrderStatus.DECLINED)) {
            throw new OrderUpdateNotAllowedException("Order status does not allow updates");
        }
    }
    @Override
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        checkIfItCanBeCancelled(order);
        order.setStatus(OrderStatus.CANCELED);
        return orderRepository.save(order);
    }

    private void checkIfItCanBeCancelled(Order order) {
        OrderStatus status = order.getStatus();
        if (status.equals(OrderStatus.FULFILLED) || status.equals(OrderStatus.UNDER_DELIVERY) || status.equals(OrderStatus.CANCELED)) {
            throw new OrderCancellationNotAllowedException("Order status cannot be canceled");
        }
    }

    public Order submitOrderForApproval(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        // kontrollojme statusin nese na lejon te bejme submission
        if (order.getStatus().equals(OrderStatus.CREATED) || order.getStatus().equals(OrderStatus.DECLINED)) {
            //kalojme ne statusin AWAITING_APPROVAL
            order.setStatus(OrderStatus.AWAITING_APPROVAL);

            return orderRepository.save(order);
        } else {
            throw new OrderSubmissionNotAllowedException("Order status does not allow submission for approval");
        }
    }

    public List<OrderDTO> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return orderMapper.toDtoList(orders);
    }

    //WAREHOUSE_MANAGER busines logic
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAllByOrderBySubmittedDateDesc();
        return orderMapper.toDtoList(orders);
    }
    public OrderDTO getOrderDetails(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            return orderMapper.toDto(order);
        } else {
            throw new OrderNotFoundException("Order not found with id: " + orderId);
        }
    }

    @Override
    public Order getOrderById(Long orderId) {
        return null;
    }

    @Override
    public void updateOrder(Order order) {

    }

    public void markOrderAsFulfilled(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        if (!order.getStatus().equals(OrderStatus.UNDER_DELIVERY)) {
            throw new OrderFulfillmentNotAllowedException("Order status does not allow fulfillment");
        }
        order.setStatus(OrderStatus.FULFILLED);

        orderRepository.save(order);
    }
}

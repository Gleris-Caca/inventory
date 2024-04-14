package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.exceptions.OrderApprovalNotAllowedException;
import com.lufthansatest.inventory.exceptions.OrderNotFoundException;
import com.lufthansatest.inventory.model.entity.Order;
import com.lufthansatest.inventory.model.enums.OrderStatus;
import com.lufthansatest.inventory.repository.OrderRepository;
import com.lufthansatest.inventory.service.OrderApprovalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderApprovalServiceImpl implements OrderApprovalService {
    private final OrderRepository orderRepository;

    public Order approveOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.AWAITING_APPROVAL) {
            throw new OrderApprovalNotAllowedException("Order is not awaiting approval");
        }
        // Approve the order
        order.setStatus(OrderStatus.APPROVED);
        return orderRepository.save(order);
    }

    public Order declineOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.AWAITING_APPROVAL) {
            throw new OrderApprovalNotAllowedException("Order is not awaiting approval");
        }

        // Decline the order
        order.setStatus(OrderStatus.DECLINED);
        //order.setDeclineReason(reason); // Set the decline reason
        return orderRepository.save(order);
    }

}
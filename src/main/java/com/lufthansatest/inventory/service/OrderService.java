package com.lufthansatest.inventory.service;

import com.lufthansatest.inventory.model.dto.OrderDTO;
import com.lufthansatest.inventory.model.entity.Order;
import com.lufthansatest.inventory.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
  //  Order addItemToOrder(Long orderId, Long itemId, int quantity);
 //   Order removeItemFromOrder(Long orderId, Long itemId);
 //   Order modifyItemQuantity(Long orderId, Long itemId, int newQuantity);
    Order cancelOrder(Long orderId);
    Order submitOrderForApproval(Long orderId);

    List<OrderDTO> getOrdersByStatus(OrderStatus status);

    //WAREHOUSE_MANAGER busines logic
    OrderDTO getOrderDetails(Long orderId);

    Order getOrderById(Long orderId);

    void updateOrder(Order order);

    void markOrderAsFulfilled(Long orderId);

    List<OrderDTO> getAllOrders();

    OrderDTO updateOrder(Long orderId, OrderDTO updatedOrderDTO);
}
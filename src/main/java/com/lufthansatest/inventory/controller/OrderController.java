package com.lufthansatest.inventory.controller;

import com.lufthansatest.inventory.exceptions.OrderApprovalNotAllowedException;
import com.lufthansatest.inventory.exceptions.OrderCancellationNotAllowedException;
import com.lufthansatest.inventory.exceptions.OrderFulfillmentNotAllowedException;
import com.lufthansatest.inventory.exceptions.OrderNotFoundException;
import com.lufthansatest.inventory.mapper.OrderMapper;
import com.lufthansatest.inventory.model.dto.OrderDTO;
import com.lufthansatest.inventory.model.entity.Order;
import com.lufthansatest.inventory.model.enums.OrderStatus;
import com.lufthansatest.inventory.service.OrderApprovalService;
import com.lufthansatest.inventory.service.OrderService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor
@Schema
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderApprovalService orderApprovalService;


    @PreAuthorize(value = "hasAnyRole('CLIENT')")
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    //plotso
    @PreAuthorize(value = "hasAnyRole('CLIENT')")
    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO updatedOrderDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(orderId, updatedOrderDTO);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }


    @PreAuthorize(value = "hasAnyRole('CLIENT')")
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        try {
            Order canceledOrder = orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order canceled successfully. Order ID: " + canceledOrder.getId());
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OrderCancellationNotAllowedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while canceling the order.");
        }
    }


    @PreAuthorize(value = "hasAnyRole('CLIENT')")
    @PostMapping("/{orderId}/submit")
    public ResponseEntity<OrderDTO> submitOrderForApproval(@PathVariable Long orderId) {
        Order submittedOrder = orderService.submitOrderForApproval(orderId);
        return new ResponseEntity<>(orderMapper.toDto(submittedOrder), HttpStatus.OK);
    }

    // E testuar me postman http://localhost:8000/order/status?status=APPROVED
    @PreAuthorize(value = "hasAnyRole('CLIENT')")
    @GetMapping("/status")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@RequestParam("status") OrderStatus status) {
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }


    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }


    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable Long orderId) {
        try {
            OrderDTO orderDTO = orderService.getOrderDetails(orderId);
            return ResponseEntity.ok(orderDTO);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @PostMapping("/{orderId}/approve")
    public ResponseEntity<OrderDTO> approveOrder(@PathVariable Long orderId) {
        Order approvedOrder = orderApprovalService.approveOrder(orderId);
        OrderDTO orderDTO = orderMapper.toDto(approvedOrder);
        return ResponseEntity.ok(orderDTO);
    }


    //e testtuar me postman
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @PostMapping("/{orderId}/decline")
    public ResponseEntity<OrderDTO> declineOrder(@PathVariable Long orderId) {
      //  Order declinedOrder = orderApprovalService.declineOrder(orderId, reason); @RequestParam String reason
        Order declinedOrder = orderApprovalService.declineOrder(orderId);
        OrderDTO orderDTO = orderMapper.toDto(declinedOrder);
        return ResponseEntity.ok(orderDTO);
    }

    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    // Exception handling
    @ExceptionHandler({OrderNotFoundException.class, OrderApprovalNotAllowedException.class})
    public ResponseEntity<String> handleOrderException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


    //e testuar me postman behet vetem kur eshte under_delivery
    @PreAuthorize(value = "hasAnyRole('WAREHOUSE_MANAGER')")
    @PutMapping("/{orderId}/fulfill")
    public ResponseEntity<String> markOrderAsFulfilled(@PathVariable Long orderId) {
        try {
            orderService.markOrderAsFulfilled(orderId);
            return ResponseEntity.ok("Order marked as fulfilled successfully.");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OrderFulfillmentNotAllowedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while marking the order as fulfilled.");
        }
    }

}


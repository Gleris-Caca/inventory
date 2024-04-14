package com.lufthansatest.inventory.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lufthansatest.inventory.model.entity.Order;
import com.lufthansatest.inventory.model.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data

public class OrderDTO {
    private Long id;
    private String orderNumber;
    private Date submittedDate;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private Date deadlineDate;
    private String reason;

    public OrderDTO(Long id, String orderNumber, Date submittedDate, OrderStatus status, List<OrderItemDTO> items, Date deadlineDate, String reason) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.submittedDate = submittedDate;
        this.status = status;
        this.items = items;
        this.deadlineDate = deadlineDate;
        this.reason=reason;
    }

    public OrderDTO() {

    }

    public OrderDTO(Order savedOrder) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

package com.lufthansatest.inventory.model.entity;

import com.lufthansatest.inventory.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", unique = true)
    private String orderNumber;

    @Column(name = "submitted_date")
    private Date submittedDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items;

    @Column(name = "deadline_date")
    private Date deadlineDate;

    @Column(name = "decline_reason")
    private String reason;




    public Order(Long id, String orderNumber, Date submittedDate, OrderStatus status, List<OrderItem> items, Date deadlineDate, String reason) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.submittedDate = submittedDate;
        this.status = status;
        this.items = items;
        this.deadlineDate = deadlineDate;
        this.reason=reason;
    }

    public Order() {
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public void setDeclineReason(String reason) {
        this.reason=reason;
    }

    public void setDeliveryDate(Date deliveryDate) {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
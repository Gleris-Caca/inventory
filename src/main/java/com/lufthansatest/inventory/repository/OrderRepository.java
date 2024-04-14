package com.lufthansatest.inventory.repository;

import com.lufthansatest.inventory.model.entity.Order;
import com.lufthansatest.inventory.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);

    List<Order> findAllByOrderBySubmittedDateDesc();
}
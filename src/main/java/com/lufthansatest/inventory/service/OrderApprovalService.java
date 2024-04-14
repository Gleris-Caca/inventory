package com.lufthansatest.inventory.service;

import com.lufthansatest.inventory.model.entity.Order;

public interface OrderApprovalService {
    Order approveOrder(Long orderId);

    //Order declineOrder(Long orderId, String reason); nuk me njeh reason
    Order declineOrder(Long orderId);


}

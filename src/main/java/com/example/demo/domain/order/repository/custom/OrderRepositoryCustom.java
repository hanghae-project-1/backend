package com.example.demo.domain.order.repository.custom;

import com.example.demo.domain.order.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepositoryCustom {

	Order getOrderWithFullDetails(UUID orderId);

	List<Order> getOrdersWithFullDetails(UUID userId);

}

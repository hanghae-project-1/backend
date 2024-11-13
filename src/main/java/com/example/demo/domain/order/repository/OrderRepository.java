package com.example.demo.domain.order.repository;

import com.example.demo.domain.entity.Order;
import com.example.demo.domain.order.repository.custom.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>, OrderRepositoryCustom {
	
	List<Order> findAllByStoreIdAndCreatedAtBetween(UUID storeId, LocalDateTime startDate, LocalDateTime endDate);
}

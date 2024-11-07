package com.example.demo.domain.order.service;

import com.example.demo.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

}

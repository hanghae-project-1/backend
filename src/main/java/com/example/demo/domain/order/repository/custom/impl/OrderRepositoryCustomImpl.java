package com.example.demo.domain.order.repository.custom.impl;

import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.repository.custom.OrderRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static com.example.demo.domain.entity.QMenu.menu;
import static com.example.demo.domain.order.entity.QOrder.order;
import static com.example.demo.domain.order.entity.QOrderDetail.orderDetail;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Order getOrderWithFullDetails(UUID orderId) {

		return queryFactory.selectFrom(order)
				.leftJoin(order.orderDetailList, orderDetail)
				.fetchJoin()
				.leftJoin(orderDetail.menu, menu)
				.fetchJoin()
				.where(order.id.eq(orderId))
				.fetchOne();
	}

	@Override
	public List<Order> getOrdersWithFullDetails(UUID userId) {

		return queryFactory.selectFrom(order)
				.leftJoin(order.orderDetailList, orderDetail)
				.fetchJoin()
				.leftJoin(orderDetail.menu, menu)
				.fetchJoin()
				.where(order.createdBy.eq(userId))
				.fetch();
	}
}

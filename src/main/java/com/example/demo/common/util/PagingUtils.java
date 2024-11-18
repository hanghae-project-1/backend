package com.example.demo.common.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.domain.store.entity.QStore.store;

public class PagingUtils<T> {

	public static Pageable setDefaultSort(Pageable pageable) {

		int size = 10;

		if (validateSize(pageable.getPageSize())) {
			size = pageable.getPageSize();
		}

		return PageRequest.of(
				pageable.getPageNumber(),
				size,
				Sort.by(
						Sort.Order.asc("createdAt"),
						Sort.Order.asc("updatedAt")
				)
		);
	}

	public static boolean validateSize(int size) {
		return size == 10 || size == 30 || size == 50;
	}

	public static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
		Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
		return new OrderSpecifier(order, fieldPath);
	}

	public static List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {

		List<OrderSpecifier<?>> orders = new ArrayList<>();

		if (!pageable.getSort().isEmpty()) {

			for (Sort.Order order : pageable.getSort()) {

				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

				switch (order.getProperty()) {
					case "storeName" -> orders.add(getSortedColumn(direction, store, "name"));
					case "ownerName" -> orders.add(getSortedColumn(direction, store, "ownerName"));
					case "avgRating" -> orders.add(getSortedColumn(direction, store, "avgRating"));
					default -> throw new IllegalArgumentException();
				}
			}
		} else {
			orders.add(getSortedColumn(Order.ASC, store, "createdAt"));
			orders.add(getSortedColumn(Order.ASC, store, "updatedAt"));
		}
		return orders;
	}
}
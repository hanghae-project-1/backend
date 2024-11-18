package com.example.demo.domain.store.repository.custom.impl;

import com.example.demo.common.util.PagingUtils;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.store.dto.response.StoreResponseDto;
import com.example.demo.domain.store.repository.custom.StoreCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static com.example.demo.common.util.PagingUtils.validateSize;
import static com.example.demo.domain.order.entity.QOrder.order;
import static com.example.demo.domain.review.entity.QReview.review;
import static com.example.demo.domain.store.entity.QStore.store;

@RequiredArgsConstructor
public class StoreCustomRepositoryImpl implements StoreCustomRepository {

	private final JPAQueryFactory queryFactory;

	public List<StoreResponseDto> searchByFilters(UUID categoryId, UUID regionId, Boolean isManager, Pageable pageable) {

		BooleanBuilder builder = new BooleanBuilder();
		List<OrderSpecifier<?>> orders = PagingUtils.getAllOrderSpecifiers(pageable);

		if (validateSize(pageable.getPageSize())) {
			pageable = PageRequest.of(pageable.getPageNumber(), 10);
		}

		if (!isManager) {
			builder.and(store.isPublic.eq(true));
			builder.and(store.isDelete.eq(false));
		}
		if (categoryId != null && regionId == null) {
			builder.and(store.categoryMenu.id.eq(categoryId));
		}

		if (regionId != null && categoryId == null) {
			builder.and(store.region.id.eq(regionId));
		}

		if (regionId != null && categoryId != null) {
			builder.and(store.categoryMenu.id.eq(categoryId)
					.and(store.region.id.eq(regionId)));
		}

		return queryFactory.select(
						Projections.constructor(StoreResponseDto.class,
								store.id.as("id"),
								store.name.as("name"),
								store.phone.as("phone"),
								store.address.as("address"),
								store.avgRating.as("avgRating"),
								store.categoryMenu.name.as("categoryMenuName"),
								store.region.district.as("district"))
				).from(store)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.orderBy(orders.toArray(OrderSpecifier[]::new))
				.where(builder)
				.fetch();

	}

	public List<StoreResponseDto> searchStoreByOwner(String ownerName, String keyWord) {

		BooleanBuilder builder = new BooleanBuilder();

		if (keyWord != null) {
			builder.and(store.name.containsIgnoreCase(keyWord));
		}

		return queryFactory.select(
						Projections.constructor(StoreResponseDto.class,
								store.id.as("id"),
								store.name.as("name"),
								store.phone.as("phone"),
								store.address.as("address"),
								store.avgRating.as("avgRating"),
								store.categoryMenu.name.as("categoryMenuName"),
								store.region.district.as("district")
						)
				).from(store)
				.where(store.ownerName.eq(ownerName).and(builder))
				.fetch();
	}

	public List<Review> searchStoreReviews(UUID storeId) {

		return queryFactory.selectFrom(review)
				.leftJoin(review.order, order)
				.fetchJoin()
				.leftJoin(order.store, store)
				.fetchJoin()
				.where(store.id.eq(storeId))
				.fetch();
	}

}

package com.example.demo.domain.store.repository.custom;

import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.store.dto.response.StoreResponseDto;

import java.util.List;
import java.util.UUID;

public interface StoreCustomRepository {

	List<StoreResponseDto> searchByFilters(UUID categoryId, UUID regionId);

	List<StoreResponseDto> searchStoreByOwner(String ownerName, String keyWord);

	List<Review> searchStoreReviews(UUID storeId);
}

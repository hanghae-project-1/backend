package com.example.demo.domain.review.repository;

import com.example.demo.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
	Page<Review> findAllByCreatedAt(String userId, Pageable pageable);

	Page<Review> findAllByCreatedAtAndIsDeleteFalseAndIsPublicTrue(String userId, Pageable pageable);

	Page<Review> findAllByIsDeleteTrueAndIsPublicFalseAndOrderStoreId(UUID storeId, Pageable pageable);
}

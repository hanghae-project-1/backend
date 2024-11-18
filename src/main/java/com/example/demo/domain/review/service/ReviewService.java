package com.example.demo.domain.review.service;

import com.example.demo.domain.entity.common.Status;
import com.example.demo.domain.order.exception.IsNotYourOrderException;
import com.example.demo.domain.order.exception.NotFoundOrderException;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.review.exception.IsNotYourReviewException;
import com.example.demo.domain.review.exception.NotFoundReviewException;
import com.example.demo.domain.review.exception.PurchaseIsNotConfirmedException;
import com.example.demo.domain.review.mapper.ReviewMapper;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import com.example.demo.domain.review.model.response.ReviewListResponseDTO;
import com.example.demo.domain.review.repository.ReviewRepository;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.exception.NotFoundStoreException;
import com.example.demo.domain.store.repository.StoreRepository;
import com.example.demo.domain.user.common.exception.UserException;
import com.example.demo.domain.user.common.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;

import static com.example.demo.common.util.PagingUtils.setDefaultSort;
import static com.example.demo.domain.entity.common.Status.Order.ORDER_COMPLETED;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final UserService userService;
	private final ReviewMapper reviewMapper;
	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;
	private final ReviewRepository reviewRepository;

	@Transactional
	public void createReview(@Valid ReviewRequestDTO request) {


		orderRepository.findById(request.orderId()).ifPresentOrElse(order -> {

					validateOrderByUser(order.getCreatedBy(), userService.getCurrentUsername());
					validateOrderStatus(order.getStatus());

					Review review = reviewMapper.toEntity(request);

					calculateForRating(order.getStore().getId(), review);

					reviewRepository.save(review);
				}, () -> {
					throw new NotFoundOrderException();
				}
		);

	}

	@Transactional
	public void modifyReviewStatus(UUID reviewId) {

		reviewRepository.findById(reviewId).ifPresentOrElse(review -> {

			String currentUsername = userService.getCurrentUsername();
			validateOrderByUser(review.getOrder().getCreatedBy(), currentUsername);
			validateReviewByUser(review.getCreatedBy(), currentUsername);

			review.markAsDelete(currentUsername);

			reviewRepository.save(review);
		}, () -> {
			throw new NotFoundReviewException();
		});

	}

	@Transactional(readOnly = true)
	public ReviewListResponseDTO getUserReviewList(String userId, Pageable pageable) {

		if (pageable.getSort().isEmpty()) {
			pageable = setDefaultSort(pageable);
		}

		Page<Review> userReviewList = Page.empty();

		try {
			String currentUserRole = userService.getCurrentUserRole();

			if (currentUserRole.equals("ROLE_MANAGER") || currentUserRole.equals("ROLE_MASTER")) {
				userReviewList = reviewRepository.findAllByCreatedAt(userId, pageable);
			}
		} catch (UserException ignored) {

		} finally {
			if (userReviewList.isEmpty()) {
				userReviewList = reviewRepository.findAllByCreatedAtAndIsDeleteFalseAndIsPublicTrue(userId, pageable);
			}
		}

		return new ReviewListResponseDTO(
				userReviewList.getNumberOfElements(),
				userReviewList.getContent().stream().map(reviewMapper::toReviewResponseDTO).toList()
		);
	}

	@Transactional(readOnly = true)
	public ReviewListResponseDTO getStoreReviewList(UUID storeId, Pageable pageable) {

		if (pageable.getSort().isEmpty()) {
			pageable = setDefaultSort(pageable);
		}

		Page<Review> storeReviewList = reviewRepository.findAllByIsDeleteTrueAndIsPublicFalseAndOrderStoreId(storeId, pageable);

		return new ReviewListResponseDTO(
				storeReviewList.getNumberOfElements(),
				storeReviewList.getContent().stream().map(reviewMapper::toReviewResponseDTO).toList()
		);
	}

	private void validateReviewByUser(String createBy, String userId) {
		if (!createBy.equals(userId)) {
			throw new IsNotYourReviewException();
		}
	}

	private void validateOrderStatus(Status.Order status) {
		if (!status.equals(ORDER_COMPLETED)) {
			throw new PurchaseIsNotConfirmedException();
		}
	}

	private void validateOrderByUser(String createBy, String userId) {
		if (!createBy.equals(userId)) {
			throw new IsNotYourOrderException();
		}
	}

	private void calculateForRating(UUID storeId, Review review) {

		Store store = storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
		List<Review> reviews = storeRepository.searchStoreReviews(storeId);

		reviews.add(review);

		OptionalDouble average = reviews.stream().mapToDouble(Review::getRating).average();
		average.ifPresent(store::updateAvgRating);

	}
}

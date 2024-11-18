package com.example.demo.domain.store.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.store.controller.docs.StoreControllerDocs;
import com.example.demo.domain.store.dto.request.StoreRequestDto;
import com.example.demo.domain.store.dto.response.StoreDetailResponseDto;
import com.example.demo.domain.store.dto.response.StoreListResponseDto;
import com.example.demo.domain.store.dto.response.StoreResponseDto;
import com.example.demo.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreController implements StoreControllerDocs {

	private final StoreService storeService;

	@PostMapping("/create")
	public Response<Void> createStore(@Valid @RequestBody StoreRequestDto requestDto) {

		storeService.createStore(requestDto);

		return Response.<Void>builder()
				.code(HttpStatus.CREATED.value())
				.message(HttpStatus.CREATED.getReasonPhrase())
				.build();

	}

	@GetMapping
	public Response<List<StoreResponseDto>> ownerStores(
			@RequestParam String ownerName,
			@RequestParam(required = false) String keyWord
	) {
		return Response.<List<StoreResponseDto>>builder()
				.data(storeService.ownerStore(ownerName, keyWord))
				.build();
	}

	@GetMapping("/search")
	public Response<StoreListResponseDto> searchStores(
			@RequestParam(required = false) UUID categoryId,
			@RequestParam(required = false) UUID regionId,
			Pageable pageable
	) {
		return Response.<StoreListResponseDto>builder()
				.data(storeService.searchStores(categoryId, regionId, pageable))
				.build();
	}

	@GetMapping("/{storeId}")
	public Response<StoreDetailResponseDto> getStoreDetail(@PathVariable UUID storeId) {
		return Response.<StoreDetailResponseDto>builder()
				.data(storeService.getStoreDetail(storeId))
				.build();
	}

	@PatchMapping("/{storeId}")
	public Response<Void> modifyStore(@PathVariable UUID storeId, @RequestBody StoreRequestDto requestDto) {

		storeService.modifyStore(storeId, requestDto);

		return Response.<Void>builder()
				.build();
	}

	@DeleteMapping("/{storeId}")
	public Response<Void> deleteStore(@PathVariable UUID storeId) {

		storeService.deleteStore(storeId);

		return Response.<Void>builder()
				.build();
	}
}

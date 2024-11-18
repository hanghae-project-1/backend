package com.example.demo.domain.store.dto.response;

import java.util.List;

public record StoreListResponseDto(

		Integer totalElements,

		List<StoreResponseDto> storeList

) {
}

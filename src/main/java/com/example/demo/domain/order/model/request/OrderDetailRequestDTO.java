package com.example.demo.domain.order.model.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record OrderDetailRequestDTO(

		@NotBlank
		UUID menuId,

		@NotBlank
		Integer price,

		@NotBlank
		Integer quantity

) {
}

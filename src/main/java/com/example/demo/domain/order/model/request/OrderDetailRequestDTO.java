package com.example.demo.domain.order.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderDetailRequestDTO(

		@NotBlank
		UUID menuId,

		@NotNull
		Integer price,

		@NotNull
		Integer quantity

) {
}

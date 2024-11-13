package com.example.demo.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryMenuRequestDto(

        @NotBlank(message = "카테고리를 입력해주세요. 예) 한식")
        String name

) {
}

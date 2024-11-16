package com.example.demo.domain.menu.dto.request;

import com.example.demo.domain.entity.common.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MenuRequestDto(

        @NotBlank(message = "메뉴 이름을 입력해주세요.")
        String name,

        @NotBlank(message = "메뉴에 대한 설명을 입력해주세요.")
        String content,

        @NotNull(message = "메뉴 가격을 입력해주세요.")
        Integer price,

        @NotNull(message = "재고 상태를 입력해주세요.")
        Boolean stockStatus,

        String imageUrl,

        @NotNull(message = "메뉴의 카테고리를 입력해주세요.")
        Status.Classification classification

) {
}

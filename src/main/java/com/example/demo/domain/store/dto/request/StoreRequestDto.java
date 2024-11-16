package com.example.demo.domain.store.dto.request;

import com.example.demo.domain.entity.common.CommonConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record StoreRequestDto(

        @NotBlank(message = "음식점 이름을 입력해주세요.")
        String name,

        String ownerName,

        @NotBlank(message = "음식점 전화번호를 입력해주세요.")
        @Pattern(regexp = CommonConstant.RegExp.PHONE, message = "유효한 전화번호 형식이 아닙니다. 예) 000-0000-0000")
        String phone,

        @NotBlank(message = "음식점 상세주소를 입력해주세요.")
        String address,

        @NotNull(message = "카테고리 ID를 입력해주세요.")
        UUID categoryMenuId,

        @NotNull(message = "지역 ID를 입력해주세요.")
        UUID regionId

) {
}
package com.example.demo.domain.region.dto.request;

import com.example.demo.domain.entity.common.CommonConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegionRequestDto (

        @NotBlank(message = "음식점 지역을 입력해주세요. 예) 서울특별시 OO구")
        @Pattern(regexp = CommonConstant.RegExp.DISTRICT)
        String district
) {
}

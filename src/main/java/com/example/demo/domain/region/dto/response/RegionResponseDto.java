package com.example.demo.domain.region.dto.response;

import java.util.UUID;

public record RegionResponseDto(
        UUID id,

        String district
) {
}

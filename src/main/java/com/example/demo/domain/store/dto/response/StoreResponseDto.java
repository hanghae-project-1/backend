package com.example.demo.domain.store.dto.response;

import java.util.UUID;

public record StoreResponseDto(

        UUID id,

        String name,

        String phone,

        String address,

        String categoryMenuName,

        String district

) {
}

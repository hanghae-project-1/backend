package com.example.demo.domain.menu.dto.response;

import com.example.demo.domain.entity.common.Status;

public record MenuResponseDto(

        String name,

        String content,

        Integer price,

        Boolean stockStatus,

        String imageUrl,

        Status.Classification classification

) {
}
